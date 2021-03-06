package org.apache.harmony.awt.datatransfer;
/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/** 
 * @author Pavel Dolgov
 * @version $Revision$
 */


/**
 * Thread for handling data transfer native events
 */
public class DataTransferThread extends Thread {
    
    private final DTK dtk;

    public DataTransferThread(DTK dtk) {
        super("AWT-DataTransferThread"); //$NON-NLS-1$
        setDaemon(true);
        this.dtk = dtk;
    }
    
    @Override
    public void run() {
        synchronized (this) {
            try {
                dtk.initDragAndDrop();
            } finally {
                notifyAll();
            }
        }
        dtk.runEventLoop();
    }
    
    @Override
    public void start() {
        synchronized (this) {
            super.start();
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
