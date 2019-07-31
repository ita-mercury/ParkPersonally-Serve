package com.parkpersonally.controller;

import com.parkpersonally.configuration.AuthProvider;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TagController.class)
public class TagControllerTest {
    @MockBean
    private TagService tagService;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthProvider authProvider;

    @Test
    public void should_return_all_tags_when_get_all_tags() throws Exception {
        //given
        Tag firstTag = new Tag("帅");
        Tag secondTag = new Tag("美");
        Tag thirdTag = new Tag("白");
        Tag fourthTag = new Tag("成熟");
        List<Tag> tags = new ArrayList<>();
        tags.add(firstTag);
        tags.add(secondTag);
        tags.add(thirdTag);
        tags.add(fourthTag);
        given(tagService.getAllTags()).willReturn(tags);
        //when
        mvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }
}
