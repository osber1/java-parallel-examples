package com.osvaldas.learning.parallel.api.client;

import com.osvaldas.learning.parallel.domain.github.GitHubPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.osvaldas.learning.parallel.util.CommonUtil.startTimer;
import static com.osvaldas.learning.parallel.util.CommonUtil.timeTaken;

@Slf4j
@RequiredArgsConstructor
public class GitHubJobsClient {
    private final WebClient webClient;

    public List<GitHubPosition> invokeGitHubJobsApiWithPageNumber(int page, String description) {
        String uri = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", page)
                .buildAndExpand()
                .toUriString();

        log.info("Uri: {}", uri);

        return webClient.get().uri(uri)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();
    }

    public List<GitHubPosition> invokeGitHubJobsApiUsingMultiplePageNumbers(List<Integer> pages, String description) {
        startTimer();
        List<GitHubPosition> listOfPositions = pages.stream()
                .map(pn -> invokeGitHubJobsApiWithPageNumber(pn, description))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        timeTaken();
        return listOfPositions;
    }

    public List<GitHubPosition> invokeGitHubJobsApiUsingMultiplePageNumbersCompletableFuture(List<Integer> pages, String description) {
        startTimer();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pages.stream()
                .map(p -> CompletableFuture.supplyAsync(() -> invokeGitHubJobsApiWithPageNumber(p, description)))
                .collect(Collectors.toList());

        List<GitHubPosition> listOfPositions = gitHubPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        timeTaken();
        return listOfPositions;
    }

    public List<GitHubPosition> invokeGitHubJobsApiUsingMultiplePageNumbersCompletableFuture_withAnyOf(List<Integer> pages, String description) {
        startTimer();
        List<CompletableFuture<List<GitHubPosition>>> gitHubPositions = pages.stream()
                .map(p -> CompletableFuture.supplyAsync(() -> invokeGitHubJobsApiWithPageNumber(p, description)))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(gitHubPositions.toArray(new CompletableFuture[0]));

        List<GitHubPosition> listOfPositions = allOf.thenApply(v -> gitHubPositions.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()))
                .join();

        timeTaken();
        return listOfPositions;
    }
}