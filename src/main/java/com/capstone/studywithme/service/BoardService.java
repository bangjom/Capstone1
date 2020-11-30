package com.capstone.studywithme.service;


import com.capstone.studywithme.controller.BoardController.PostDto;
import com.capstone.studywithme.domain.Board;
import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.domain.Post;
import com.capstone.studywithme.domain.PostContent;
import com.capstone.studywithme.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long creatPost(Post post){
        boardRepository.savePost(post);
        return post.getId();
    }

    @Transactional
    public Long create(Board board){
        validateDuplicateBoard(board);
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void createPostContent(PostContent postContent) {
        boardRepository.savePostContent(postContent);
    }

    private void validateDuplicateBoard(Board board){
        List<Board> findBoards = boardRepository.findByName(board.getName());
        if(!findBoards.isEmpty()){
            throw new IllegalStateException("이미 존재하는 방입니다.");
        }
    }

    public List<Board> findBoards(){return boardRepository.findAll();}

    public Board findOne(Long boardId){return boardRepository.findOne(boardId);}

    public Board findOneByName(String name){
        return boardRepository.findByName(name).get(0);}


    public List<PostDto> findPosts(Long boardId) {return boardRepository.findPosts(boardId);}


}

