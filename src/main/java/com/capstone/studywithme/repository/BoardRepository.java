package com.capstone.studywithme.repository;

import com.capstone.studywithme.controller.BoardController.PostDto;
import com.capstone.studywithme.domain.Board;
import com.capstone.studywithme.domain.Member;
import com.capstone.studywithme.domain.Post;
import com.capstone.studywithme.domain.PostContent;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board){em.persist(board);}

    public Board findOne(Long id){return em.find(Board.class, id);}

    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class)
            .getResultList();
    }

    public List<Board> findByName(String name){
        return em.createQuery("select b from Board b  where b.name= :name", Board.class)
            .setParameter("name",name)
            .getResultList();
    }

    public List<PostDto> findPosts(Long boardId) {
        return em.createQuery("select pc from PostContent pc inner join pc.post p where  p.board.id = :id",
            PostContent.class)
            .setParameter("id",boardId)
            .getResultList()
            .stream()
            .map(m -> {
                return new PostDto(m.getPost().getName(),m.getPost().getMember().getEmail(),m.getContent(),m.getPost().getCreated_at());
            })
            .collect(Collectors.toList());
    }

    public void savePost(Post post) {
        em.persist(post);
    }

    public void savePostContent(PostContent postContent) {
        em.persist(postContent);
    }
}
