package com.parkpersonally.service;

import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
}
