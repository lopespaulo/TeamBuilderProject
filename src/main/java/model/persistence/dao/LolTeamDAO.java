package model.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.entities.LolTeam;
import model.entities.LolTeam;

public class LolTeamDAO implements GenericDAO<LolTeam, Integer> {
	private Session currentSession;
	private Transaction currentTransaction;

	public LolTeamDAO() {
	}
	
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");
		configuration.addAnnotatedClass(LolTeam.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public LolTeam findById(Integer id) {
		LolTeam player = (LolTeam) getCurrentSession().get(LolTeam.class, id);
		return player;
	}

	public void insert(LolTeam player) {
		getCurrentSession().save(player);
	}

	public void delete(LolTeam player) {
		getCurrentSession().delete(player);
	}

	public void update(LolTeam player) {
		getCurrentSession().update(player);
	}

	@SuppressWarnings("unchecked")
	public List<LolTeam> findAll() {
		List<LolTeam> players = (List<LolTeam>) getCurrentSession().createQuery("FROM LolTeam").list();
		return players;
	}
	
	public void deleteAll(){
		List<LolTeam> players = findAll();
		for (LolTeam player : players)
			delete(player);
	}}
