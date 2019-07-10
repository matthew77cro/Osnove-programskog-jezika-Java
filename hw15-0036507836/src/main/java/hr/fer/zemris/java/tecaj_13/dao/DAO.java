package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.models.BlogComment;
import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;

public interface DAO {
	
	BlogEntry getBlogEntry(Long id);

    List<BlogEntry> getBlogEntries(BlogUser user);

    BlogUser getUser(String nick);

    List<BlogUser> getUsers();

    void storeBlogUser(BlogUser user);

    void storeBlogEntry(BlogEntry entry);

    void storeBlogComment(BlogComment comment);
	
}