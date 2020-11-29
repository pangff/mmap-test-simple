package com.pffair.mmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@WebListener
public class MmapServletContextListener implements ServletContextListener {

    boolean isStop = false;
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        isStop = true;
        logger.info("mmap: contextDestroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        isStop = false;
        startMmapTest();
        logger.info("mmap: contextInitialized");
    }

    private static int count = 31457280; // 30 MB

    private void startMmapTest(){
        RandomAccessFile memoryMappedFile = null;
        MappedByteBuffer out = null;
        try {
            memoryMappedFile = new RandomAccessFile("mmap-test.log", "rw");
        } catch (FileNotFoundException e) {
            logger.error("create RandomAccessFile failed",e);
            e.printStackTrace();
        }
        try {
            out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
        } catch (IOException e) {
            logger.error("create MappedByteBuffer failed",e);
            e.printStackTrace();
        }
        for (int i = 0; i < count; i++) {
            out.put((byte) 'M');
        }
        logger.info("Writing to Memory Mapped File is completed");

        for (int i = 0; i < 10; i++) {
            logger.info("Reading >> "+(char) out.get(i));
        }
        logger.info("Reading from Memory Mapped File is completed");
        try {
            memoryMappedFile.close();
        } catch (IOException e) {
            logger.error("close MappedByteBuffer failed",e);
            e.printStackTrace();
        }

        try {
            if(!isStop){
                Thread.sleep(2000);
                startMmapTest();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
