package org.example.ch4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Q: 회색 색조의 모든 꽃을 보라색으로 합성할 것
 *
 */
public class MultiThreadForFlower {
    public static final String SOURCE_FILE = "./image/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
//        recolorSingleThread(originalImage, resultImage);
        recolorMultiThread(originalImage, resultImage, 3);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println(duration); // 성능 측정

        // 저장할 파일 객체 생성
        File outputFile = new File(DESTINATION_FILE);

        // 디렉토리가 존재하지 않는 경우 생성
        outputFile.getParentFile().mkdirs();

        // 이미지를 JPG 형식으로 파일에 쓰기
        ImageIO.write(resultImage, "jpg", outputFile);
    }

    public static void recolorSingleThread(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorMultiThread(BufferedImage originalImage, BufferedImage resultImage, int numberOfThread){
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThread;

        for(int i=0; i<numberOfThread; i++){
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int leftCorner = 0; // x좌표
                int topCorner = height * threadMultiplier; // y좌표

                recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
            });

            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }

    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
        // 처리할 이미지 영역을 leftCorner에서 시작하여 width만큼의 너비를 의미함 & 경계 검사
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            // 처리할 이미지 영역을 topCorner 시작하여 height만큼의 너비를 의미함 & 경계 검사
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed = 0;
        int newGreen = 0;
        int newBlue = 0;

        if (isShadeOfGray(red, green, blue)) {
            // 보라 색조로 바꿔
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        // Red, Green, Blue가 균등하게 섞여 있는 경우는 회색이다
        // 색상간의 근접성을 확인하기 위해 임의의 거리30을 둠(경험상)
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(red - blue) < 30;
    }

    // pixel에서 rgb를 추출하기 위한 메소드
    public static int getRed(int rgb) {
        // 비트 시프트 연산을 사용하는 주된 이유는 특정 비트 패턴의 값을 쉽게 사용하거나 추출하기 위함이다.
        return (rgb & 0x00FF0000) >> 16;
    }

    // pixel에서 rgb를 추출하기 위한 메소드
    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    // pixel에서 rgb를 추출하기 위한 메소드
    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

    // pixel에 rgb를 넣을 메소드
    public static int createRGBFromColors(int red, int green, int blue) {
        // 비트마스크는 어떤 비트를 남기고, 어떤 비트를 제거할지를 정의하는 역할을 한다.
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;
        rgb |= 0xFF000000;

        return rgb;
    }
}
