package com.tourman.app.services;

import java.util.List;
import java.util.Map;

public interface CloudinaryService {
    public List<String> searchImageUrlsByTag(String tag) throws Exception;
}
