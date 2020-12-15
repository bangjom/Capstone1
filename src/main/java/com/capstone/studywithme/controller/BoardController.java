package com.capstone.studywithme.controller;


import com.capstone.studywithme.domain.Board;
import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.domain.Post;
import com.capstone.studywithme.domain.PostContent;
import com.capstone.studywithme.service.BoardService;
import com.capstone.studywithme.service.MemberService;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @PostMapping("/board")
    public CreateBoardResponse createBoard(@RequestBody @Valid  CreateBoardRequest request) {
        Board board = new Board();
        board.setName(request.getName());
        Long id = boardService.create(board);
        return new CreateBoardResponse(id,board.getName());
    }

    @PostMapping("/board/post")
    public CreatePostResponse createPost(@RequestBody @Valid CreatePostRequest request){
        Post post = new Post();
        PostContent postContent = new PostContent();
        Member findmember =  memberService.findOneByEmail(request.getEmail());
        Board  findboard =  boardService.findOneByName(request.getBoard());
        post.setMember(findmember);
        post.setName(request.getName());
        post.setBoard(findboard);
        post.setCreated_at(LocalDateTime.now());
        post.setUpdated_at(LocalDateTime.now());
        Long postId = boardService.creatPost(post);
        postContent.setPost(post);
        postContent.setContent(request.getContent());
        boardService.createPostContent(postContent);
        return new CreatePostResponse(postId,post.getName());
    }

    @GetMapping("/board")
    public Result searchBoard(@RequestParam(value="name") @Valid String name){
        Board findBoard = boardService.findOneByName(name);
        List<PostDto> posts = boardService.findPosts(findBoard.getId());
        return new Result(posts.size(), posts);
    }

    @GetMapping("/board/")
    public PostResponse searchPost(@RequestParam(value="name") @Valid String name,
                                   @RequestParam(value="id") @Valid Long id){
        Board findBoard = boardService.findOneByName(name);
        PostDto posts = boardService.findOnePosts(findBoard.getId(), id);
        return new PostResponse(posts.getTitle(), posts.getContent());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class PostDto{
        private Long id;
        private String title;
        private String email;
        private String content;
        private LocalDateTime date;
    }

    @Data
    @AllArgsConstructor
    static class CreateBoardResponse{
        private Long id;
        private String name;
    }

    @Data
    static class CreateBoardRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class PostResponse{
        private String title;
        private String content;
    }

    @Data
    static class PostRequest {
        private Long id;
    }



    @Data
    @AllArgsConstructor
    static class CreatePostResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreatePostRequest {
        private String board;
        private String name;
        private String email;
        private String content;
    }
}
