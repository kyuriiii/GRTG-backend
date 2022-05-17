package grooteogi.controller;

import grooteogi.dto.PostDto;
import grooteogi.dto.ScheduleDto;
import grooteogi.response.BasicResponse;
import grooteogi.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping
  public ResponseEntity<BasicResponse> search(
      @RequestParam(name = "keyword", required = false) String keyword,
      @RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "sort", required = false) String sort) {

    List<PostDto.SearchResponse> posts =
        postService.search(keyword, sort, PageRequest.of(page - 1, 12));
    return ResponseEntity.ok(BasicResponse.builder().data(posts).build());
  }

  @GetMapping("/{postId}")
  public ResponseEntity<BasicResponse> getPostResponse(@PathVariable int postId) {
    PostDto.Response post = postService.getPostResponse(postId);
    return ResponseEntity.ok(BasicResponse.builder().data(post).build());
  }

  @GetMapping("/schedule/{postId}")
  public ResponseEntity<BasicResponse> getSchedulesResponse(@PathVariable int postId) {
    List<ScheduleDto.Response> schedulesResponse = postService.getSchedulesResponse(postId);
    return ResponseEntity.ok(BasicResponse.builder().data(schedulesResponse).build());
  }

  @GetMapping("/review/{postId}")
  public ResponseEntity<BasicResponse> getReviewsResponse(@PathVariable int postId) {
    List<PostDto.ReviewResponse> reviewResponses = postService.getReviewsResponse(postId);
    return ResponseEntity.ok(BasicResponse.builder().data(reviewResponses).build());
  }

  @PostMapping
  public ResponseEntity<BasicResponse> createPost(@RequestBody PostDto.Request request) {
    PostDto.CreateResponse createdPost = this.postService.createPost(request);
    return ResponseEntity.ok(BasicResponse.builder().data(createdPost).build());
  }

  @PutMapping("/{postId}")
  public ResponseEntity<BasicResponse> modifyPost(@RequestBody PostDto.Request request,
      @PathVariable int postId) {
    PostDto.Response modifiedPost = this.postService.modifyPost(request, postId);
    return ResponseEntity.ok(BasicResponse.builder().data(modifiedPost).build());
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<BasicResponse> deletePost(@PathVariable int postId) {
    postService.deletePost(postId);
    return ResponseEntity.ok(
        BasicResponse.builder().message("delete post success").build());
  }
}
