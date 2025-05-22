package com.tourman.app.services.impls;

import com.cloudinary.Cloudinary;
import com.cloudinary.Search;
import com.tourman.app.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public List<String> searchImageUrlsByTag(String tag) throws Exception{
        Map result = cloudinary.search()
                .expression("tags:" + tag)
                .execute();

        List<Map<String, Object>> resources = (List<Map<String, Object>>) result.get("resources");

        return resources.stream()
                .map(r -> (String) r.get("secure_url"))
                .collect(Collectors.toList());
    }

}
