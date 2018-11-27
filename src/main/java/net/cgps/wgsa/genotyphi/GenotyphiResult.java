package net.cgps.wgsa.genotyphi;

import net.cgps.wgsa.genotyphi.core.GenotyphiMutation;
import net.cgps.wgsa.genotyphi.core.GenotyphiSchema;
import net.cgps.wgsa.genotyphi.lib.Jsonnable;
import net.cgps.wgsa.genotyphi.lib.MutationSearchResult;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenotyphiResult extends Jsonnable {

  private final String assemblyId;
  private final String genotype;
  private final Collection<Map.Entry<String, Collection<GenotyphiMutation>>> genotyphiMutations;
  private final Collection<GenotyphiSchema.GenotyphiGroup> assignments;
  private final Collection<MutationSearchResult> blastResults;
  private final int foundLoci;


  @SuppressWarnings("unused")
  private GenotyphiResult() {

    this("", "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Integer.MIN_VALUE);
  }

  public GenotyphiResult(final String assemblyId, final String genotype, final Collection<Map.Entry<String, Collection<GenotyphiMutation>>> genotyphiMutations, final Collection<GenotyphiSchema.GenotyphiGroup> assignments, final Collection<MutationSearchResult> blastResults, final int foundLoci) {

    this.assemblyId = assemblyId;
    this.genotype = genotype;
    this.genotyphiMutations = genotyphiMutations;
    this.assignments = assignments;
    this.blastResults = blastResults;
    this.foundLoci = foundLoci;
  }

  public String getGenotype() {

    return this.genotype;
  }

  public Collection<Map.Entry<String, Collection<GenotyphiMutation>>> getGenotyphiMutations() {

    return this.genotyphiMutations;
  }

  public Collection<MutationSearchResult> getBlastResults() {

    return this.blastResults;
  }

  public int getFoundLoci() {

    return this.foundLoci;
  }

  public String getAssemblyId() {
    return this.assemblyId;
  }

  public Collection<GenotyphiSchema.GenotyphiGroup> getAssignments() {
    return this.assignments;
  }
}
