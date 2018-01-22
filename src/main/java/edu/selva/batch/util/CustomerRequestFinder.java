package edu.selva.batch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is Utility class helps to identify the max customer request which can be fit in the given
 * section.
 *
 * @author Selva Dharmaraj
 * @since 2018-01-22
 * @see edu.selva.batch.util.TicketRequestHandler
 */
public class CustomerRequestFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRequestFinder.class);

  /**
   * This utility method has algorithm to identify the maximum customer requests which can be fit in
   * the given section. This method finds all possible sub set combination matching the sum
   * (section) and returns the sub set contain maximum customers.
   *
   * @param requests available requests for the given section.
   * @param sectionCount count of given section
   * @return Nothing.
   */
  public static List<Integer> findEligibleCustomerRequests(
      List<Integer> requests, int sectionCount) {
    Set<List> combinationOfRequest = new HashSet<>();

    if (requests == null || requests.isEmpty()) {
      LOGGER.info(
          String.format(
              "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
              sectionCount, requests, requests));
      return null;
    }

    if (requests.size() == 1 && requests.get(0) == sectionCount) {
      LOGGER.info(
          String.format(
              "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
              sectionCount, requests, requests));
      return requests;
    }

    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    while (true) {
      int i = stack.peek();
      if (i == requests.size() - 1) {
        stack.pop();
        if (stack.isEmpty()) {
          break;
        }
        int last = stack.pop();
        stack.push(last + 1);
      } else {
        if (i == 0
            && stack.stream().map(e -> requests.get(e)).mapToInt(Integer::intValue).sum()
                == sectionCount) {
          List<Integer> fitInRequest =
              stack.stream().mapToInt(e -> requests.get(e)).boxed().collect(Collectors.toList());
          LOGGER.debug("Special scenario...");
          LOGGER.debug("List: :" + fitInRequest);
          combinationOfRequest.add(fitInRequest);
        }
        stack.push(i + 1);
      }
      if (stack.stream().map(e -> requests.get(e)).mapToInt(Integer::intValue).sum()
          == sectionCount) {
        List<Integer> fitInRequest =
            stack.stream().mapToInt(e -> requests.get(e)).boxed().collect(Collectors.toList());
        LOGGER.debug("List: :" + fitInRequest);
        combinationOfRequest.add(fitInRequest);
      }
    }

    Optional<List> max = combinationOfRequest.stream().max(Comparator.comparing(List::size));
    List uniqueCombinationRequest = max.isPresent() ? max.get() : null;
    LOGGER.info(
        String.format(
            "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
            sectionCount, requests, uniqueCombinationRequest));
    return uniqueCombinationRequest;
  }
}
