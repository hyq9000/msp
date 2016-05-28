package com.mmm.clouds.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.TemporalType;

import com.common.dbutil.MyBatisEntity;



/**
 * The persistent class for the t_spread_path database table.
 * 
 */
@Entity
@Table(name="t_spread_path")
@MyBatisEntity(namespace = "SpreadPath")
public class SpreadPath extends com.common.dbutil.Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pathId;
	private Long clueId;
	private String contentTitle;
	private Long readerCid;
	private String readerNick;
	private Long spreadCid;
	private String spreadNick;
	private Long userContentId;
	private Short spreadDeep;
	private Byte spreadType;
	private String spreadHead;
	private String readerHead;

	public SpreadPath() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PATH_ID")
	public String getPathId() {
		return this.pathId;
	}

	public void setPathId(String pathId) {
		this.pathId = pathId;
	}


	@Column(name="CLUE_ID")
	public Long getClueId() {
		return this.clueId;
	}

	public void setClueId(Long clueId) {
		this.clueId = clueId;
	}


	@Column(name="CONTENT_TITLE")
	public String getContentTitle() {
		return this.contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}


	@Column(name="READER_CID")
	public Long getReaderCid() {
		return this.readerCid;
	}

	public void setReaderCid(Long readerCid) {
		this.readerCid = readerCid;
	}


	@Column(name="READER_NICK")
	public String getReaderNick() {
		return this.readerNick;
	}

	public void setReaderNick(String readerNick) {
		this.readerNick = readerNick;
	}


	@Column(name="SPREAD_CID")
	public Long getSpreadCid() {
		return this.spreadCid;
	}

	public void setSpreadCid(Long spreadCid) {
		this.spreadCid = spreadCid;
	}


	@Column(name="SPREAD_NICK")
	public String getSpreadNick() {
		return this.spreadNick;
	}

	public void setSpreadNick(String spreadNick) {
		this.spreadNick = spreadNick;
	}


	@Column(name="USER_CONTENT_ID")
	public Long getUserContentId() {
		return this.userContentId;
	}

	public void setUserContentId(Long userContentId) {
		this.userContentId = userContentId;
	}

	@Column(name="SPREAD_DEEP")
	public Short getSpreadDeep() {
		return spreadDeep;
	}


	public void setSpreadDeep(Short spreadDeep) {
		this.spreadDeep = spreadDeep;
	}

	@Column(name="SPREAD_TYPE")
	public Byte getSpreadType() {
		return spreadType;
	}


	public void setSpreadType(Byte spreadType) {
		this.spreadType = spreadType;
	}

	@Column(name="SPREAD_HEAD")
	public String getSpreadHead() {
		return spreadHead;
	}


	public void setSpreadHead(String spreadHead) {
		this.spreadHead = spreadHead;
	}

	
	@Column(name="READER_HEAD")
	public String getReaderHead() {
		return readerHead;
	}

	
	public void setReaderHead(String readerHead) {
		this.readerHead = readerHead;
	}

	

}