package com.medixpress.commercial.sqlite;

import java.io.IOException;

public class DemoDatabase {
	// Creates a demonstration database
	
	private void loadDictionary() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    createDemoDatabase();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

	private void createDemoDatabase() throws IOException {
		Vendor v1 = new Vendor(0, String name, String location, String hours);
	}
}