package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.models.BlogComment;
import hr.fer.zemris.java.hw15.models.BlogEntry;
import hr.fer.zemris.java.hw15.models.BlogUser;
import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;

public class JPADAOImpl implements DAO {

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		List<BlogEntry> entries = JPAEMProvider.getEntityManager().createQuery(
				"SELECT entry FROM BlogEntry as entry WHERE entry.creator=:c", BlogEntry.class)
				.setParameter("c", user).getResultList();
		return entries;
	}
	
	@Override
	public BlogUser getUser(String nick) {
		List<BlogUser> userList = JPAEMProvider.getEntityManager().createQuery(
				"SELECT user FROM BlogUser as user WHERE user.nick=:n", BlogUser.class)
				.setParameter("n", nick).getResultList();
		return userList==null || userList.isEmpty() ? null : userList.get(0);
	}

	@Override
	public List<BlogUser> getUsers() throws DAOException {
		List<BlogUser> userList = JPAEMProvider.getEntityManager().createQuery(
				"SELECT user FROM BlogUser as user", BlogUser.class).getResultList();
		return userList;
	}

	@Override
	public void storeBlogUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		if(user.getId() == null) em.persist(user);
		else em.merge(user);
	}

	@Override
	public void storeBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		if(entry.getId() == null) em.persist(entry);
		else em.merge(entry);
	}

	@Override
	public void storeBlogComment(BlogComment comment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		if(comment.getId() == null) em.persist(comment);
		else em.merge(comment);
	}

	@Override
	public BlogEntry getBlogEntry(Long id) {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

}