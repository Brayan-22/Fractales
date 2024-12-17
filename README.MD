# Fractal Image Recorder

This project is a Java application that allows users to generate and record fractal images into a video file. The application provides a graphical user interface (GUI) for interacting with the fractal generation and recording process.

## What are fractal
Fractals are a phenomenon always present in nature, and this project aims to showcase how amazing they can be. The program will graph various fractals generated through the analysis of the complex plane, first studied by mathematician Benoît Mandelbrot. These fractals can be graphically represented thanks to an escape-time algorithm, where the different colors indicate the speed at which points on the complex plane diverge or converge in a recursive sequence.
## Features

- Generate fractal images
- Zoom and navigate through the fractal
- Record a sequence of fractal images into a video file in AVI format

## Technologies Used

- Java
- Maven
- OpenCV (for image processing)
- JavaCV (for video recording)
- Swing (for GUI)

## Getting Started

### Requirements
Add the following dependencies to your pom.xml file
```xml
<!-- https://mvnrepository.com/artifact/org.bytedeco/javacv -->
<dependency>
    <groupId>org.bytedeco</groupId>
    <artifactId>javacv</artifactId>
    <version>1.5.9</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.bytedeco/javacv-platform -->
<dependency>
    <groupId>org.bytedeco</groupId>
    <artifactId>javacv-platform</artifactId>
    <version>1.5.9</version>
</dependency>
```
### Prerequisites

- Java 11 or higher
- Maven
- OpenCV 4.7.0 installed locally
- OpenCV 4.7.0 java/bin folder added to the system PATH
### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Brayan-22/fractal-image-recorder.git
   cd fractal-image-recorder
    ```

2. Build the project:
3. ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
    java -jar target/fractales.jar
    ```
## Usage

1. Launch the application by running the main class `FractalImageRecorder`.
2. Use the GUI to generate and navigate through the fractal.
3. Use the "START" button to start generating the fractal.
4. Use click over the canvas to zoom in and out of the fractal.
5. Use the arrow keys to navigate through the fractal.
6. Click the "RECORD" button to start recording the fractal images.
7. The recorded video will be saved in the folder who you choose.
8. The video will be saved in the folder you choose.


## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for details.

## Developer 
Brayan Alejandro Riveros Rodriguez 👨‍💻
Systems engineering student at the district university, Bogotá