package com.bsw.groupware.teambox.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsw.groupware.common.MsgEntity;
import com.bsw.groupware.dashboard.service.DashBoardService;
import com.bsw.groupware.document.service.DocumentService;
import com.bsw.groupware.login.service.LoginService;
import com.bsw.groupware.login.service.NaverService;
import com.bsw.groupware.model.CryptoUtils;
import com.bsw.groupware.model.FileMetadata;
import com.bsw.groupware.model.NaverNewsResponseVO;
import com.bsw.groupware.model.NaverVO;
import com.bsw.groupware.model.TeamsVO;
import com.bsw.groupware.model.UserVO;
import com.bsw.groupware.teambox.service.TeamboxService;
import com.nimbusds.jose.shaded.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@Slf4j
public class TeamboxController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private TeamboxService teamboxService;
	
    @Value("${upload.dir}")
    private String baseUploadDir;
	
    @RequestMapping("/teambox.ex")
    public String showTeamList(HttpSession session, 
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        String user = (String) session.getAttribute("user");
        List<TeamsVO> teams = dashBoardService.getTeamsTitle(user);

        // 한 페이지에 아이템 표시 갯수
        int pageSize = 8;
        int totalItems = teams.size();  // 전체 아이템 갯수
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);  // 전체 페이지 수

        // 현재 페이지에 해당하는 아이템 리스트 추출
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<TeamsVO> paginatedTeams = teams.subList(fromIndex, toIndex);

        model.addAttribute("teams", paginatedTeams);
        model.addAttribute("currentPage", page);  // 현재 페이지
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수

        return "/teambox/teamboxList";
    }
    
    @RequestMapping("/teambox/write.ex")
    public String writeTeambox(HttpSession session, Model model, @RequestParam(value = "action") String action) {
    	logger.debug("writeTeambox start");
        String user = (String) session.getAttribute("user");
        List<TeamsVO> teams = dashBoardService.getTeamsTitle(user);
        
        model.addAttribute("action", action);

        return "/teambox/write";
    }
    
    @RequestMapping("/teams/detail.ex")
    public String datail(HttpSession session, Model model, 
    					@RequestParam(value = "title") String title, 
    					@RequestParam(value = "seq") String seq) {
    	logger.debug("datail Start");
    	logger.debug("title value ::{}", title);
    	logger.debug("seq value ::{}", seq);
    	
    	TeamsVO teamsVO = new TeamsVO();
    	teamsVO = teamboxService.getDetail(title, seq);
    	
    	logger.debug("teamsVO value :: {}", teamsVO.toString());

        model.addAttribute("teamsVO", teamsVO);
        
    	List<FileMetadata> fileMetadatas= documentService.getFileData(teamsVO.getFileId());
    	
        if (fileMetadatas != null) {
            for (FileMetadata fileMetadata : fileMetadatas) {
                String encryptedFileId;
				try {
					encryptedFileId = CryptoUtils.encrypt(fileMetadata.getFileId());
	                fileMetadata.setFileId(encryptedFileId); // 암호화된 파일 ID로 설정
				} catch (Exception e) {
	                logger.error("Error encrypting file ID", e);
				}
                logger.debug("File Metadata - Original Filename: {}, File Path: {}", fileMetadata.getOriginalFilename(), fileMetadata.getFilePath());
            }
        } else {
            logger.debug("No file metadata found for file ID: {}", teamsVO.getFileId());
        }
        
        model.addAttribute("fileMetadatas", fileMetadatas);

        
    	return "teambox/detail";
    }
    
    @RequestMapping("/teambox/save.ex")
    public String saveTeambox(HttpSession session, 
    						  Model model, 
    						  @RequestParam("title") String title,
    						  @RequestParam("content") String contents,
    						  @RequestParam("file") MultipartFile[] attachments,
    						  RedirectAttributes redirectAttributes) {
    	logger.debug("save start");
    	logger.debug("title value :: {}", title);
    	logger.debug("content value :: {}", contents);
    	logger.debug("attachments value :: {}", attachments);

        String user = (String) session.getAttribute("user");
        
    	TeamsVO teamVO = new TeamsVO();
    	teamVO.setTitle(title);
    	teamVO.setContents(contents);
    	teamVO.setRegistUserId(user);
    	
        long currentTime = System.currentTimeMillis();
        int randomNum = new Random().nextInt(1000);
    	String fileId = null;
        
        for(MultipartFile file : attachments) {
    		if(!file.isEmpty()) {
    			fileId = String.format("%d%03d", currentTime, randomNum);
    			teamVO.setFileId(fileId);
    		}
        }
        
        teamboxService.saveTeambox(teamVO);
        
        // 첨부파일 처리 시작
        String datePattern = "yyyyMMdd";
        String dateFolder = new SimpleDateFormat(datePattern).format(new Date());
        File uploadDir = new File(baseUploadDir, dateFolder);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // 폴더가 없으면 생성
            logger.debug("Created directory: {}", uploadDir.getAbsolutePath());
        }
        
        List<String> attachmentPaths = new ArrayList<>();
        try {
        	for(MultipartFile file : attachments) {
        		if(!file.isEmpty()) {
                    // 고유 파일 이름 설정 (fileId를 사용하여 파일명 포함)
                    String originalFilename = file.getOriginalFilename();
                    String newFilename = fileId + "_" + originalFilename;
                    File saveFile = new File(uploadDir, newFilename);

                    // 파일 저장
                    file.transferTo(saveFile);
                    
                    FileMetadata fileMetadata = new FileMetadata();
                    
                    fileMetadata.setFileId(fileId);
                    fileMetadata.setOriginalFilename(originalFilename);
                    fileMetadata.setStoredFilename(newFilename);
                    fileMetadata.setUploadDate(LocalDateTime.now());
                    fileMetadata.setSubmitterId(user);
                    fileMetadata.setFilePath(saveFile.getAbsolutePath());

                    documentService.insertFile(fileMetadata);

                    // 저장된 파일 경로를 리스트에 추가
                    attachmentPaths.add(saveFile.getAbsolutePath());
                    logger.debug("attachments :: {}", file.getOriginalFilename());
        		}
        	}
		} catch (Exception e) {
			redirectAttributes.addAttribute("message", "첨부파일 오류");
            logger.error("File upload error", e);
		}
    	// 첨부파일 처리 종료
    	
        return "redirect:/teambox.ex";
    }

}
