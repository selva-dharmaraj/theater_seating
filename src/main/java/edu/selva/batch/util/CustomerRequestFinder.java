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
      int[] array, int pos, int sum, int[] acc, Set<List<Integer>> results) {
    if (Arrays.stream(acc).sum() > sum) {
      return;
    }

    if (Arrays.stream(acc).sum() == sum) {
      //System.out.println(Arrays.toString(acc));
      results.add(Arrays.stream(acc).boxed().collect(Collectors.toList()));

      return;
    }

    for (int i = pos + 1; i < array.length; i++) {
      int[] newAcc = new int[acc.length + 1];
      System.arraycopy(acc, 0, newAcc, 0, acc.length);
      newAcc[acc.length] = array[i];
      collectCombination(array, i, sum, newAcc, results);
    }
  }
  // ~------------------------------------------------------------------------------------------------------------------

  /**
   * This utility method has algorithm to identify the maximum customer requests which can be fit in
   * the given section. This method finds all possible sub set combination matching the sum
   * (section) and returns the sub set contain maximum customers.
   *
   * @param requests available requests for the given section.
   * @param sectionCount count of given section
   * @return Nothing.
   */
  public static List<Integer> findEligibleCustomerRequests(int[] requests, int sectionCount) {
    Set<List<Integer>> combinationOfRequest = new HashSet<>();

    collectCombination(requests, -1, sectionCount, new int[] {}, combinationOfRequest);

    Optional<List<Integer>> max =
        combinationOfRequest.stream().max(Comparator.comparing(List::size));
    List uniqueCombinationRequest = max.isPresent() ? max.get() : null;
    LOGGER.info(
        String.format(
            "trying to fill section {%s} with available request(s) {%s} and found matched {%s}",
            sectionCount, Arrays.toString(requests), uniqueCombinationRequest));

    return uniqueCombinationRequest;
  } // end method findEligibleCustomerRequests
} // end class CustomerRequestFinder
