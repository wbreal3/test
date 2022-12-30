package com.real.testDemo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 对于file的io操作原始操作可使用i/o stream ,通过InPutStream将流中数据读到缓冲区中byte 再将缓冲区数据写入OutPutStream中可实现文件的复制操作
 * 但是stream是单向的，channel管道是双向的，可使用channel进行更加高效的操作
 * 对于日常的文件上传可使用org.apache.commons.io.FileUtils提供的copy实现文件拷贝
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

    /**
     * windows上创建文件
     * 创建文件的前提
     * @throws IOException 抛出io异常
     */
    @Test
    public void fileDemoForWindows() throws IOException {
        File file = new File("D:\\demo");
        // 创建文件夹  'new File' is redundant
        if (!file.exists() && !file.mkdirs()){
            Assert.notNull(file,"文件创建失败");
        } else {
            // 存在文件目录直接创建文件
            file = new File("D:\\demo\\demo.png");
        }
        File file1 = new File("D:\\demo\\111.png");
        if (!file1.exists()){
            Assert.notNull(file, file1.getName()+"不存在");
        }
        FileInputStream fileInputStream = new FileInputStream(file1);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        // 将文件copy到file中 1024=1kb !!! 设置此缓冲区会进行字节填充数据会是其倍数大小
        byte[] bytes = new byte[1024*1024*10];

//        FileUtils.copyFile(file1,file);
        // 流会记忆读取的位置，下一次读取时会定位到最新位置
        while (fileInputStream.read(bytes) != -1){
            fileOutputStream.write(bytes);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

}
