package edu.selva.batch.util;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is Utility class helps to identify the max customer request which can be fit in the given
 * section.
 *
 * @author Selva Dharmaraj
 * @see edu.selva.batch.util.TicketRequestHandler
 * @version 1.0, 2018-01-22
 */
public class CustomerRequestFinder {
  // ~Static-fields/initializers----------------------------------------------------------------------------------------

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRequestFinder.class);

  // ~Methods-----------------------------------------------------------------------------------------------------------

  public static void collectCombination(
      int[] array, int pos, int sum, int[] acc, Set<List<Integer>> results, boolean match) {
    if (Arrays.stream(acc).sum() > sum) {
      if (!match) {
        List<Integer> listRequests = Arrays.stream(acc).boxed().collect(Collectors.toList());
          listRequests.remove(listRequests.size() - 1);
        results.add(listRequests);
      }
      return;
    }

    if (Arrays.stream(acc).sum() == sum) {
      if (match) {
        results.add(Arrays.stream(acc).boxed().collect(Collectors.toList()));
      }

      return;
    }

    for (int i = pos + 1; i < array.length; i++) {
      int[] newAcc = new int[acc.length + 1];
      System.arraycopy(acc, 0, newAcc, 0, acc.length);
      newAcc[acc.length] = array[i];
      collectCombination(array, i, sum, newAcc, results, match);
    }
  }
  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * This utility method has algorithm to identify the maximum customer requests which can be fit in
   * the given section. This method finds all possible sub set combination matching the sum
   * (section) and returns the sub set contain maximum customers or min customers. As per multiple
   * run screnario Minimum customer option fills more seats efficiently.
   *
   * @param requests available requests for the given section.
   * @param sectionCount count of given section
   * @return Nothing.
   */
  public static List<Integer> findEligibleCustomerRequests(
      int[] requests, int sectionCount, boolean minRequests, boolean match) {
    Set<List<Integer>> combinationOfRequest = new HashSet<>();

    collectCombination(requests, -1, sectionCount, new int[] {}, combinationOfRequest, match);

    Optional<List<Integer>> result = null;
    if (minRequests) {
      result = combinationOfRequest.stream().min(Comparator.comparing(List::size));
    } else {
      result =
          combinationOfRequest
              .stream()
              .max(
                  Comparator.comparing(
                      integers -> integers.stream().mapToInt(Integer::intValue).sum()));
    }
    List uniqueCombinationRequest = result.isPresent() ? result.get() : null;
    LOGGER.info(
        String.format(
            "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
            sectionCount, Arrays.toString(requests), uniqueCombinationRequest));

    return uniqueCombinationRequest;
  } // end method findEligibleCustomerRequests
} // end class CustomerRequestFinder
