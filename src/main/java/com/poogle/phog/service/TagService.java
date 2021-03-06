package com.poogle.phog.service;

import com.poogle.phog.domain.*;
import com.poogle.phog.exception.AuthorizationException;
import com.poogle.phog.exception.NotFoundException;
import com.poogle.phog.web.note.dto.GetNoteResponseDTO;
import com.poogle.phog.web.tag.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class TagService {

    private final String API_URL = "https://dapi.kakao.com/v2/vision/multitag/generate";
    private final String KAKAO = System.getenv("KAKAO");
    private TagRepository tagRepository;
    private NoteTagRepository noteTagRepository;
    private NoteRepository noteRepository;
    private NoteService noteService;
    private final String TEST;


    public TagService(TagRepository tagRepository, NoteTagRepository noteTagRepository, NoteRepository noteRepository,
                      NoteService noteService, Environment env) {
        this.tagRepository = tagRepository;
        this.noteTagRepository = noteTagRepository;
        this.noteRepository = noteRepository;
        this.noteService = noteService;
        TEST = env.getProperty("TEST");
    }

    private static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(file);
        return file;
    }

    public GetTagListResponseDTO tagResponseDTOList(Long userId) {
        List<TagListDTO> getActivatedTags = new ArrayList<>();
        List<TagListDTO> getArchivedTags = new ArrayList<>();
        for (Tag tag : tagRepository.findTagsByUserIdAndActivatedTrueOrderByTagNameAsc(userId)) {
            TagListDTO getTagResponseDTO = TagListDTO.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .activated(tag.getActivated())
                    .frequency(noteTagRepository.countNoteTagByTagId(tag.getTagId()))
                    .build();
            getActivatedTags.add(getTagResponseDTO);
        }
        for (Tag tag : tagRepository.findTagsByUserIdAndActivatedFalseOrderByTagNameAsc(userId)) {
            TagListDTO getTagResponseDTO = TagListDTO.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .activated(tag.getActivated())
                    .frequency(noteTagRepository.countNoteTagByTagId(tag.getTagId()))
                    .build();
            getArchivedTags.add(getTagResponseDTO);
        }
        return GetTagListResponseDTO.builder()
                .activatedList(getActivatedTags)
                .archivedList(getArchivedTags)
                .build();
    }

    public void changeActiveStatus(Long userId, Long tagId, PatchTagRequestDTO request) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundException("Tag doesn't exist"));
        if (!tag.getUserId().equals(userId)) {
            throw AuthorizationException.accessWrong();
        }
        tag.setActivated(request.getActivated());
        tagRepository.save(tag);
    }

    public List<GetTagCategoryResponseDTO> tagCategoryResponseDTOList(Long userId, Pageable pageable) {
        List<GetTagCategoryResponseDTO> getCategorizedTags = new ArrayList<>();
        Page<Tag> tags = tagRepository.findAllByUserIdAndActivatedTrue(userId, pageable);
        for (Tag tag : tags) {
            List<Photo> photoList = noteRepository.findPhotoByNoteId(noteTagRepository.findRecentNoteIdByTagId(tag.getTagId()));
            GetTagCategoryResponseDTO getTagCategoryResponseDTO = GetTagCategoryResponseDTO.builder()
                    .tagId(tag.getTagId())
                    .tagName(tag.getTagName())
                    .activated(tag.getActivated())
                    .frequency(noteTagRepository.countNoteTagByTagId(tag.getTagId()))
                    .thumbnail(photoList.get(0).getUrl())
                    .build();
            getCategorizedTags.add(getTagCategoryResponseDTO);
        }
        return getCategorizedTags;
    }

    public List<GetNoteResponseDTO> getTaggedNoteList(List<Long> tags) {
        List<Long> taggedNoteIds = new ArrayList<>();
        for (Long tagId : tags) {
            List<Long> noteIdList = noteTagRepository.findNoteIdsByTagId(tagId);
            for (Long noteId : noteIdList) {
                if (!taggedNoteIds.contains(noteId)) {
                    taggedNoteIds.add(noteId);
                }
            }
        }
        List<GetNoteResponseDTO> taggedNotes = new ArrayList<>();
        for (Long noteId : taggedNoteIds) {
            Note note = noteService.findNote(noteId);
            GetNoteResponseDTO detail = GetNoteResponseDTO.builder()
                    .noteId(noteId)
                    .rawMemo(note.getRawMemo())
                    .photos(noteService.findPhotos(noteId))
                    .created(note.getCreated())
                    .tags(noteService.findTags(noteId))
                    .build();
            taggedNotes.add(detail);
        }
        Collections.sort(taggedNotes);
        return taggedNotes;
    }

    private Map<String, StringBuffer> requestTags(MultipartFile multipartFile) throws IOException {
        String HEADER = "KakaoAK " + KAKAO;
        String CRLF = "\r\n";
        String TWO_HYPHENS = "--";
        String BOUNDARY = UUID.randomUUID().toString();
        URL apiURL = new URL(API_URL);
        String fileName = multipartFile.getName();
        log.info("[*] KAKAO: {}", KAKAO);
        log.info("[*] TEST: {}", TEST);

        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", HEADER);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + BOUNDARY);

        OutputStream outputStream = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
        writer.append(TWO_HYPHENS).append(BOUNDARY).append(CRLF);
        writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"").append(fileName).append("\"").append(CRLF);
        writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName)).append(CRLF);
        writer.append("Content-Transfer-Encoding: binary").append(CRLF);
        writer.append(CRLF);
        writer.flush();

        File file = multipartToFile(multipartFile, fileName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(CRLF);
        writer.flush();

        int responseCode = connection.getResponseCode();
        BufferedReader br;
        if (responseCode == 200) {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        StringBuffer statusCode = new StringBuffer();
        statusCode.append(responseCode);

        HashMap<String, StringBuffer> apiResult = new HashMap<>();
        apiResult.put("responseCode", statusCode);
        apiResult.put("response", response);
        log.info("[*] apiResult: {}", apiResult);
        return apiResult;
    }

    public GetTagSuggestionDTO suggestTags(MultipartFile multipartFile) throws IOException {
        Map<String, StringBuffer> suggestions = requestTags(multipartFile);
        GetTagSuggestionDTO getTagSuggestionDTO;
        ArrayList<String> tagsEn = new ArrayList<>();
        ArrayList<String> tagsKr = new ArrayList<>();

        log.info("[*] suggestions : {}", suggestions);

        if (suggestions.get("responseCode").toString().equals("200")) {
            JSONObject result = new JSONObject(suggestions.get("response").toString()).getJSONObject("result");
            JSONArray labelEn = result.getJSONArray("label");
            JSONArray labelKr = result.getJSONArray("label_kr");
            for (int i = 0; i < labelKr.length(); i++) {
                tagsEn.add((String) labelEn.get(i));
                tagsKr.add((String) labelKr.get(i));
            }
        }
        getTagSuggestionDTO = GetTagSuggestionDTO.builder()
                .tagsEn(tagsEn)
                .tagsKr(tagsKr)
                .build();
        return getTagSuggestionDTO;
    }

}
