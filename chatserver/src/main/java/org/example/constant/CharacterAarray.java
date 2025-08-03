package org.example.constant;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CharacterAarray {
    public static final Map<String, BlockingQueue<String>> chatCache = new ConcurrentHashMap<>();
}
