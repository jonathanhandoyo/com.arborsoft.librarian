package com.arborsoft.librarian;

import com.arborsoft.librarian.model.Book;
import com.arborsoft.librarian.repository.BookRepository;
import com.arborsoft.librarian.repository.Neo4jTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {
    private MockMvc mockMvc;

//    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired protected WebApplicationContext wac;
    @Autowired protected Neo4jTemplate template;
    @Autowired protected BookRepository bookRepository;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
    }

    @Test
    public void test1() throws Exception {
        for (Book book: this.bookRepository.findBooksByTitle("title")) {
            long time = System.nanoTime();
            //System.out.println(book);
            System.out.println("" + book.getTitle() + " : " + (System.nanoTime() - time));
        }
    }
}
