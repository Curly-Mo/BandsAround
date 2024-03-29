/*
 * Copyright (c) 2012, the Last.fm Java Project and Committers
 * All rights reserved.
 *
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.curlymo.bandsaround.lastfm.cache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

/**
 * This Cache implementation can be used on the Google App Engine servers.
 * Note that this is only experimental, please report any errors 
 * @author Janni Kovacs
 */
public class AppEngineCache extends Cache {

        private static final String KIND = "lfm_cache";
        private static final String PREFIX = "lfm_";
        private DatastoreService datastore;

        public AppEngineCache() {
                this.datastore = DatastoreServiceFactory.getDatastoreService();
        }

        @Override
        public void clear() {
                for(Entity e: datastore.prepare(new Query(KIND)).asIterable()) {
                        datastore.delete(e.getKey());
                }
        }

        @Override
        public boolean contains(String cacheEntryName) {
                Key key = KeyFactory.createKey(KIND, PREFIX+cacheEntryName);
                try {
                        datastore.get(key);
                } catch (EntityNotFoundException e) {
                        return false;
                }
                return true;
        }

        @Override
        public InputStream load(String cacheEntryName) {
                Key key = KeyFactory.createKey(KIND, PREFIX+cacheEntryName);
                try {
                        Entity e = datastore.get(key);
                        Text t = (Text) e.getProperty("data");
                        return new ByteArrayInputStream(t.getValue().getBytes("UTF-8"));
                } catch (EntityNotFoundException e) {
                        // not found, return null
                } catch (UnsupportedEncodingException e) {
                        // never happens
                }
                return null;
        }

        @Override
        public void remove(String cacheEntryName) {
                Key key = KeyFactory.createKey(KIND, PREFIX+cacheEntryName);
                datastore.delete(key);
        }

        @Override
        public void store(String cacheEntryName, InputStream inputStream,
                        long expirationDate) {
                try {
                        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                        StringBuilder sb = new StringBuilder(inputStream.available());
                        char[] buf = new char[2048];
                        int read;
                        while ((read = reader.read(buf, 0, buf.length)) != -1) {
                                sb.append(buf, 0, read);
                        }
                        Entity e = new Entity(KIND, PREFIX+cacheEntryName);
                        e.setProperty("expirationDate", expirationDate);
                        e.setProperty("data", new Text(sb.toString()));
                        datastore.put(e);
                } catch (IOException e) {
                        // don't cache
                }
        }

        @Override
        public boolean isExpired(String cacheEntryName) {
                Key key = KeyFactory.createKey(KIND, PREFIX+cacheEntryName);
                try {
                        Entity e = datastore.get(key);
                        Long expirationDate = (Long) e.getProperty("expirationDate");
                        return expirationDate < System.currentTimeMillis();
                } catch (EntityNotFoundException e) {
                        return false;
                }
        }
}
