package net.cgps.wgsa.genotyphi.core;

import net.cgps.wgsa.genotyphi.GenotyphiResult;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResolveGenotyphi implements Function<Collection<GenotyphiSchema.GenotyphiGroup>, String> {


  @Override
  public String apply(final Collection<GenotyphiSchema.GenotyphiGroup> aggregatedAssignments) {

    // Get the deepest level
    final int deepestLevel = aggregatedAssignments
        .stream()
        .map(GenotyphiSchema.GenotyphiGroup::getDepth)
        .min(Integer::compareTo)
        .orElse(-1);

    return -1 == deepestLevel ?
           "" :
           aggregatedAssignments
               .stream()
               .filter(group -> deepestLevel == group.getDepth())
               .map(GenotyphiSchema.GenotyphiGroup::toCode)
               .collect(Collectors.joining(","));

  }

}
