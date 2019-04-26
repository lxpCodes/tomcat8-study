/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.coyote.http11.filters;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.coyote.Response;

/**
 * Test case to demonstrate the interaction between gzip and flushing in the
 * output filter.
 */
public class TestGzipOutputFilter {

    /*
     * Test the interaction between gzip and flushing. The idea is to: 1. create
     * a internal output buffer, response, and attach an active gzipoutputfilter
     * to the output buffer 2. set the output stream of the internal buffer to
     * be a ByteArrayOutputStream so we can inspect the output bytes 3. write a
     * chunk out using the gzipoutputfilter and invoke a flush on the
     * InternalOutputBuffer 4. read from the ByteArrayOutputStream to find out
     * what's being written out (flushed) 5. find out what's expected by writing
     * to GZIPOutputStream and close it (to force flushing) 6. Compare the size
     * of the two arrays, they should be close (instead of one being much
     * shorter than the other one)
     *
     * @throws Exception
     */
    @Test
    public void testFlushingWithGzip() throws Exception {
        // set up response, InternalOutputBuffer, and ByteArrayOutputStream
        Response res = new Response();
        TesterOutputBuffer tob = new TesterOutputBuffer(res, 8 * 1024);
        res.setOutputBuffer(tob);

        // set up GzipOutputFilter to attach to the TesterOutputBuffer
        GzipOutputFilter gf = new GzipOutputFilter();
        tob.addFilter(gf);
        tob.addActiveFilter(gf);

        // write a chunk out
        byte[] d = "Hello there tomcat developers, there is a bug in JDK".getBytes();
        ByteBuffer bb = ByteBuffer.wrap(d);
        tob.doWrite(bb);

        // flush the InternalOutputBuffer
        tob.flush();

        // read from the ByteArrayOutputStream to find out what's being written
        // out (flushed)
        byte[] dataFound = tob.toByteArray();

        // find out what's expected by writing to GZIPOutputStream and close it
        // (to force flushing)
        ByteArrayOutputStream gbos = new ByteArrayOutputStream(1024);
        GZIPOutputStream gos = new GZIPOutputStream(gbos);
        gos.write(d);
        gos.close();

        // read the expected data
        byte[] dataExpected = gbos.toByteArray();

        // most of the data should have been flushed out
        assertTrue(dataFound.length >= (dataExpected.length - 20));
    }
}
