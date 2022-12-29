package com.real.testDemo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 大文件复制demo 目标
 * @author real
 * @Date 2022/12/29
 */
public class CopyFileDemo {
    // 使用随机文件访问类RandomAccessFile读取文件，使用FileChannel作为输入输出管道

    public static void main(String[] args) throws IOException {
        RandomAccessFile srcFile = new RandomAccessFile("D:\\jdk-8u181-linux-x64.tar.gz", "r");
        RandomAccessFile targetFile = new RandomAccessFile("D:\\jdk1.8-linux.tar.gz", "rw");

        // 获取对应的管道对象
        FileChannel fcIn = srcFile.getChannel();
        FileChannel fcOut = targetFile.getChannel();

        // 分批copy文件流数据，500M为一个挡位
        long totalSize = fcIn.size();
        int copySize = 1024*1024*500;
        // 复制的次数
        long count = totalSize%copySize == 0 ? totalSize/copySize : totalSize/copySize+1;

        for (long i = 0; i < count; i++) {
            // 每次开始复制的位置
            long start = i*copySize;

            // 每次复制的实际数据大小
            long trueSize =  totalSize-start > copySize ? copySize : totalSize-start;

            // 将磁盘数据映射到内存
            MappedByteBuffer bufferIn = fcIn.map(FileChannel.MapMode.READ_ONLY, start, trueSize);
            MappedByteBuffer bufferOut = fcOut.map(FileChannel.MapMode.READ_WRITE, start, trueSize);

            // 数据拷贝
            for (long l = 0; l < trueSize; l++) {
                byte b = bufferIn.get();
                bufferOut.put(b);
            }
        }
        srcFile.close();
        targetFile.close();
        fcIn.close();
        fcOut.close();


    }

}
