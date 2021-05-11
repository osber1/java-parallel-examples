package com.osvaldas.learning.parallel.api.client;

import com.osvaldas.learning.parallel.domain.github.GitHubPosition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
class GitHubJobsClientTest {

    WebClient webClient = WebClient.create("https://jobs.github.com/");

    GitHubJobsClient githubClient = new GitHubJobsClient(webClient);

    @Test
    void testInvokeGitHubJobsApiWithPageNumber() {
        //given
        int pageNumber = 1;
        String description = "JavaScript";
        //when
        List<GitHubPosition> gitHubPositions = githubClient.invokeGitHubJobsApiWithPageNumber(pageNumber, description);
        log.info("Positions: {}", gitHubPositions);
        //then
        assertThat(gitHubPositions.size()).isGreaterThan(0);
        gitHubPositions.forEach(i -> assertThat(i).isNotNull());
    }

    @Test
    void testInvokeGitHubJobsApiUsingMultiplePageNumbers() {
        //given
        List<Integer> pages = List.of(1, 2, 3);
        String description = "Java";
        //when
        List<GitHubPosition> gitHubPositions = githubClient.invokeGitHubJobsApiUsingMultiplePageNumbers(pages, description);
        log.info("Positions: {}", gitHubPositions);
        //then
        assertThat(gitHubPositions.size()).isGreaterThan(0);
        gitHubPositions.forEach(i -> assertThat(i).isNotNull());
    }

    @Test
    void testInvokeGitHubJobsApiUsingMultiplePageNumbersCompletableFuture() {
        //given
        List<Integer> pages = List.of(1, 2, 3);
        String description = "Java";
        //when
        List<GitHubPosition> gitHubPositions = githubClient.invokeGitHubJobsApiUsingMultiplePageNumbersCompletableFuture(pages, description);
        log.info("Positions: {}", gitHubPositions);
        //then
        assertThat(gitHubPositions.size()).isGreaterThan(0);
        gitHubPositions.forEach(i -> assertThat(i).isNotNull());
    }
    @Test

    void testInvokeGitHubJobsApiUsingMultiplePageNumbersCompletableFuture_withAnyOf() {
        //given
        List<Integer> pages = List.of(1, 2, 3);
        String description = "Java";
        //when
        List<GitHubPosition> gitHubPositions = githubClient.invokeGitHubJobsApiUsingMultiplePageNumbersCompletableFuture_withAnyOf(pages, description);
        log.info("Positions: {}", gitHubPositions);
        //then
        assertThat(gitHubPositions.size()).isGreaterThan(0);
        gitHubPositions.forEach(i -> assertThat(i).isNotNull());
    }
}