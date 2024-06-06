package org.example.member03.controller;

import org.example.member03.service.NewsService;
import org.springframework.stereotype.Controller;
import org.example.member03.domain.News;
import jakarta.servlet.ServletContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private ServletContext servletContext;

    @Value("${news.imgdir}")
    private String imgDir;

    @GetMapping
    public String listNews(Model model) {
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newslist", newsList);
        return "ch10/newsList";
    }

    @GetMapping("/{id}")
    public String getNews(@PathVariable("id") int id, Model model) {
        News news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        model.addAttribute("imgdir", imgDir);
        return "ch10/newsView";
    }

    @GetMapping("/add")
    public String showAddNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "ch10/newsList";
    }

    @PostMapping("/add")
    public String addNews(@ModelAttribute News news, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = saveFile(file);
            news.setImg(fileName);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            news.setDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
            newsService.saveNews(news);
        } catch (IOException e) {
            servletContext.log("Error saving file", e);
            return "redirect:/news/add";
        }
        return "redirect:/news";
    }

    @GetMapping("/{id}/delete")
    public String deleteNews(@PathVariable("id") int id) {
        newsService.deleteNewsById(id);
        return "redirect:/news";
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(imgDir, file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
        return null;
    }
}