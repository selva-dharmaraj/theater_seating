package edu.selva.batch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerRequestFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRequestFinder.class);

  public static List<Integer> findEligibleCustomerRequests(List<Integer> array, int sum) {
    Set<List> combinationOfRequest = new HashSet<>();

    if (array == null || array.isEmpty()) {
      LOGGER.info(
          String.format(
              "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
              sum, array, array));
      return null;
    }

    if (array.size() == 1 && array.get(0) == sum) {
      LOGGER.info(
          String.format(
              "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
              sum, array, array));
      return array;
    }

    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    while (true) {
      int i = stack.peek();
      if (i == array.size() - 1) {
        stack.pop();
        if (stack.isEmpty()) {
          break;
        }
        int last = stack.pop();
        stack.push(last + 1);
      } else {
        if (i == 0
            && stack.stream().map(e -> array.get(e)).mapToInt(Integer::intValue).sum() == sum) {
          List<Integer> fitInRequest =
              stack.stream().mapToInt(e -> array.get(e)).boxed().collect(Collectors.toList());
          LOGGER.debug("Special scenario...");
          LOGGER.debug("List: :" + fitInRequest);
          combinationOfRequest.add(fitInRequest);
        }
        stack.push(i + 1);
      }
      if (stack.stream().map(e -> array.get(e)).mapToInt(Integer::intValue).sum() == sum) {
        List<Integer> fitInRequest =
            stack.stream().mapToInt(e -> array.get(e)).boxed().collect(Collectors.toList());
        LOGGER.debug("List: :" + fitInRequest);
        combinationOfRequest.add(fitInRequest);
      }
    }

    // combinationOfRequest.stream().forEach(System.out::println);
    Optional<List> max = combinationOfRequest.stream().max(Comparator.comparing(List::size));
    List uniqueCombinationRequest = max.isPresent() ? max.get() : null;
    LOGGER.info(
        String.format(
            "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
            sum, array, uniqueCombinationRequest));
    return uniqueCombinationRequest;
  }
}
