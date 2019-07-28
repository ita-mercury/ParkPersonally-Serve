package com.parkpersonally.service;

import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
}
