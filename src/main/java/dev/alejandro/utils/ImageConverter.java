package dev.alejandro.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.GaussianBlur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ImageConverter {
    private ImageConverter() {
    }
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void saveAsPng(BufferedImage img, String path,boolean gaussianBlur) {
        try {
            File file = new File(path);
            ImageIO.write(img, "png", file);

            if (gaussianBlur){
                Mat image = Imgcodecs.imread(path);
                if (image.empty()) {
                    throw new IllegalArgumentException("The image is empty");
                }
                GaussianBlur(image, image, new Size(3, 3), 0);
                Imgcodecs.imwrite(path, image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Mat sharpenImage(Mat image) {
        if (image.empty()) {
            throw new IllegalArgumentException("The image is empty");
        }
        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        kernel.put(0,0,
                0, -1, 0,
                -1, 5, -1,
                0, -1, 0
                );
        Mat sharpendedImage = new Mat();
        Imgproc.filter2D(image, sharpendedImage, -1, kernel);
        return sharpendedImage;
    }

    public static void recordFractal(JFrame parent,String imagesPath,String output,int videoDuration,double speed) throws IllegalArgumentException{
        File dir = new File(imagesPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("The directory is empty");
        }
        int fps = Math.max(1,files.length/videoDuration);
        fps = Math.max(1,(int)(fps*speed));
        List<String> links = List.of(files).stream().map(File::getAbsolutePath).toList();
        convertPngtoMovie(parent,links,output,fps);
    }

    public static void convertPngtoMovie(JFrame parent,List<String> links,String vidPath,int fps) {
        if (links == null || links.isEmpty()) {
            throw new IllegalArgumentException("The list of images is null or empty");
        }
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        //Leer la primera imagen para determinar el tamaño del video
        Mat firstImage = Imgcodecs.imread(links.getFirst());
        if (firstImage.empty()) {
            throw new IllegalArgumentException("The first image is empty");
        }
        int width = firstImage.width();
        int height = firstImage.height();

        //Configurar el grabador de video

        try(FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath, width, height)){
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setVideoOption("rc_mode", "bitrate");
            recorder.setVideoOption("enable_skip_frame", "0");
            recorder.setFormat("avi");
            recorder.setFrameRate(fps);
            recorder.setVideoBitrate(10*1024*1024);
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            recorder.start();
            //Mostrar la barra de progreso
            JDialog progressDialog = new JDialog(parent,"Procesando...",true);
            progressDialog.setSize(300, 100);
            progressDialog.setLayout(new BorderLayout());
            progressDialog.setLocationRelativeTo(null);
            // Crear la barra de progreso
            JProgressBar progressBar = new JProgressBar(0, links.size());
            progressBar.setStringPainted(true);
            progressDialog.add(new JLabel("Procesando..."), BorderLayout.NORTH);
            progressDialog.add(progressBar, BorderLayout.CENTER);
            AtomicBoolean isRunning = new AtomicBoolean(true);
            SwingWorker<Void,Integer> worker = new SwingWorker<Void, Integer>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (String link : links) {
                        Mat image = Imgcodecs.imread(link);
                        if (image.empty()) {
                            throw new IllegalArgumentException("The image is empty");
                        }
                        image = sharpenImage(image);
                        Frame frame = converter.convert(image);
                        recorder.record(frame);
                        image.release();
                        publish(links.indexOf(link));
                    }
                    return null;
                }

                protected void process(List<Integer> chunks) {
                    progressBar.setValue(chunks.get(0));
                }
                protected void done() {
                    progressDialog.dispose();
                }
            };
            worker.execute();
            progressDialog.setVisible(true);
            while (isRunning.get()) {
                try {
                    Thread.sleep(10);
                    if (worker.isDone()) {
                        isRunning.set(false);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            recorder.stop();
            recorder.release();
        } catch (FrameRecorder.Exception e) {
            throw new RuntimeException("Error starting the recorder", e);
        }finally {
            firstImage.release();
        }
    }
}
