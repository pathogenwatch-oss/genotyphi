package net.cgps.wgsa.genotyphi.core;

import net.cgps.wgsa.genotyphi.lib.Jsonnable;

import java.util.*;
import java.util.stream.Stream;

public class GenotyphiSchema extends Jsonnable {

  private final Collection<GenotyphiGene> genes;

  @SuppressWarnings("unused")
  private GenotyphiSchema() {

    this(Collections.emptyList());
  }

  public GenotyphiSchema(final Collection<GenotyphiGene> genes) {

    this.genes = genes;
  }

  public Collection<GenotyphiGene> getGenes() {
    return this.genes;
  }

  public Stream<Map.Entry<String, GenotyphiGene>> asEntries() {

    return this.genes.stream().map(gene -> new AbstractMap.SimpleImmutableEntry<>(gene.getSequenceId(), gene));
  }

  public static class GenotyphiGroup {

    private final int depth;
    private final List<String> code;

    @SuppressWarnings("unused")
    private GenotyphiGroup() {

      this(0, Collections.emptyList());
    }

    private GenotyphiGroup(final int depth, final List<String> code) {

      this.depth = depth;
      this.code = code;
    }

    public static GenotyphiGroup build(final String code) {

      final String[] codeArr = code.split("\\.");
      return new GenotyphiGroup(codeArr.length, Arrays.asList(codeArr));
    }

    public int getDepth() {

      return this.depth;
    }

    public List<String> getCode() {
      return this.code;
    }

    @Override
    public int hashCode() {
      return Objects.hash(depth, code);
    }

    @Override
    public boolean equals(final Object o) {

      if (this == o) {
        return true;
      }
      if ((null == o) || (this.getClass() != o.getClass())) {
        return false;
      }

      final GenotyphiGroup that = (GenotyphiGroup) o;

      if (this.depth != that.depth) {
        return false;
      }
      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      return this.code.equals(that.code);
    }

    @Override
    public String toString() {

      return this.toCode() + " (Depth=" + this.depth + ")";
    }

    public String toCode() {

      return String.join(".", this.code);
    }

    public boolean isParentOf(final GenotyphiGroup included) {

      if (included.getDepth() <= this.getDepth()) {
        return false;
      }

      for (int i = 0; i < this.code.size(); i++) {
        if (! this.code.get(i).equals(included.code.get(i))) {
          return false;
        }
      }
      return true;
    }
  }

}
