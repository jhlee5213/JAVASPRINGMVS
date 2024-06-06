package org.example.member03.service;

import org.example.member03.domain.News;
import org.example.member03.repository.NewsRepository;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNewsById(int id) {
        return newsRepository.findById(id).orElse(null);
    }

    public void saveNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNewsById(int id) {
        newsRepository.deleteById(id);
    }

}