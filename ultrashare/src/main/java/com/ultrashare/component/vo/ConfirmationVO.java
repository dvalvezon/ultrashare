package com.ultrashare.component.vo;

import java.util.Collections;
import java.util.List;

import com.ultrashare.model.Share;

public class ConfirmationVO {

	private final List<Share> shareList;

	public ConfirmationVO(List<Share> shareList) {
		this.shareList = Collections.unmodifiableList(shareList);
	}

	public List<Share> getUnmodifiableShareList() {
		return this.shareList;
	}
}
