package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class ReactiveTryWithResourcesUsing {

    public static void main(String args[]) {
        //testTryWithResources();
        testTryWithResourcesIfExceptionHappen();
    }

    /**
     * Open file                  1330106945
     * Reading file and handle it 1330106945
     * Result HELLO WORLD         1330106945
     * Close file                 1330106945
     * Stream finished
     */
    private static void testTryWithResources() {
        Flux<String> flux = Flux.using(FileHandler::new, fileHandler -> Flux.just(fileHandler.handleFileData()), FileHandler::dispose);
        flux.subscribe(
                data -> System.out.println("Result " + data),
                e -> System.out.println("Error " + e.getMessage()),
                () -> System.out.println("Stream finished"));
    }

    private static void testTryWithResourcesIfExceptionHappen() {
        Flux<String> flux = Flux.using(FileHandlerWithException::new, fileHandler -> Flux.just(fileHandler.handleFileData()), FileHandlerWithException::dispose);
        flux.subscribe(
                data -> System.out.println("Result " + data),
                e -> System.out.println("Error " + e.getMessage()),
                () -> System.out.println("Stream finished"));
    }

    /**
     * Open file 1279149968
     * Close file 1279149968
     * Error Unknown error! I close file, like in finally block
     */
    //You may do it AutoCloseable, or any - it doesn't matter
    private static class FileHandler implements Disposable {

        private static final String FILE_OPEN = "OPEN";
        private static final String FILE_CLOSED = "CLOSED";

        private String fileDescriptor = "UNDEFINED";
        private String fileContent = "hello world";

        public FileHandler() {
            openFile();
        }

        public void openFile() {
            System.out.println("Open file " + this.hashCode());
            fileDescriptor = FILE_OPEN;
        }

        public String handleFileData() {
            System.out.println("Reading file and handle it " + this.hashCode());
            fileContent = fileContent.toUpperCase();
            return fileContent + " " + this.hashCode();
        }

        @Override
        public void dispose() {
            System.out.println("Close file " + this.hashCode());
            fileDescriptor = FILE_CLOSED;
        }

        @Override
        public boolean isDisposed() {
            boolean disposed = FILE_CLOSED.equals(fileDescriptor);
            System.out.println("Check if file closed. Result is = '" + disposed + " " + this.hashCode());
            return disposed;
        }
    }

    private static class FileHandlerWithException extends FileHandler {

        @Override
        public String handleFileData() {
            if (true){
                throw new RuntimeException("Unknown error! I close file, like in finally block");
            }
            return super.handleFileData();
        }
    }
}
