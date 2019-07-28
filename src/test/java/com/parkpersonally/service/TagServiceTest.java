package com.parkpersonally.service;

import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TagServiceTest {
    private TagRepository tagRepository;
    private TagService tagService;

    @Before
    public void initTest() {
        tagRepository = mock(TagRepository.class);
        tagService = new TagService(tagRepository);
    }

    @Test
    public void should_return_all_the_tags_when_find_all_tags(){
        //given
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("帅"));
        tags.add(new Tag("美"));
        tags.add(new Tag("白"));
        tags.add(new Tag("成熟"));
        given(tagRepository.findAll()).willReturn(tags);
        //when
        List<Tag> foundTags=tagService.getAllTags();
        //then
        assertSame(4,foundTags.size());
    }
}
