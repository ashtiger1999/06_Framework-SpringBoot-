package edu.kh.project.board.model.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface EditBoardService {

	/** 게시글 작성 서비스
	 * @param inputBoard
	 * @param images
	 * @return
	 */
	int boardInsert(Board inputBoard, List<MultipartFile> images) throws Exception;

}
