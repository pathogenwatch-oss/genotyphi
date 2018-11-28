package net.cgps.wgsa.genotyphi.core;

import net.cgps.wgsa.genotyphi.GenotyphiResult;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResolveGenotyphi implements Function<Collection<GenotyphiSchema.GenotyphiGroup>, String> {


  @Override
  public String apply(final Collection<GenotyphiSchema.GenotyphiGroup> aggregatedAssignments) {

    final int deepestLevel = aggregatedAssignments
        .stream()
        .map(GenotyphiSchema.GenotyphiGroup::getDepth)
        .max(Integer::compareTo)
        .orElse(-1);

    return -1 == deepestLevel ?
           "" :
           aggregatedAssignments
               .stream()
               .filter(group -> deepestLevel == group.getDepth())
               .map(GenotyphiSchema.GenotyphiGroup::toCode)
               .sorted()
               .collect(Collectors.joining(","));

//    if (aggregatedAssignments.isEmpty()) {
//      return "";
//    }
//
//    // Report all groups that aren't a parent of another that is present
//
//    final List<GenotyphiSchema.GenotyphiGroup> sorted = new ArrayList<>(aggregatedAssignments);
//
//    sorted.sort(Comparator.comparingInt(GenotyphiSchema.GenotyphiGroup::getDepth).reversed());
//
//    final Collection<GenotyphiSchema.GenotyphiGroup> nameGroups = new ArrayList<>(5);
//
//    for (final GenotyphiSchema.GenotyphiGroup newGroup : sorted) {
//      boolean keep = true;
//      for (final GenotyphiSchema.GenotyphiGroup included : nameGroups) {
//        if (newGroup.isParentOf(included)) {
//          keep = false;
//          break;
//        }
//      }
//      if (keep) {
//        nameGroups.add(newGroup);
//      }
//    }
//
//    return nameGroups
//        .stream()
//        .map(GenotyphiSchema.GenotyphiGroup::toCode)
//        .collect(Collectors.joining(","));
  }

}
