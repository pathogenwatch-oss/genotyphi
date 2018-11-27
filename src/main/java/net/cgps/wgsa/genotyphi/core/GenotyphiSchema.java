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

  public Stream<Map.Entry<String, GenotyphiGene>> asEntries() {

    return this.genes.stream().map(gene -> new AbstractMap.SimpleImmutableEntry<>(gene.getSequenceId(), gene));
  }

  public enum Depth {
    PRIMARY(0), CLADE(1), SUBCLADE(2), GROUP(3), SUBGROUP(4);

    private final int index;

    Depth(final int index) {

      this.index = index;
    }

    public static Depth maxDepth() {

      return Stream.of(Depth.values()).max(Comparator.comparingInt(Depth::getIndex)).get();
    }

    public static Optional<Depth> toDepth(final int levels) {

      return Stream.of(Depth.values()).filter(depth -> (depth.getIndex() + 1) == levels).findFirst();
    }

    public int getIndex() {

      return this.index;
    }
  }

  public static class GenotyphiGroup {

    // Deepest level of the genotyphi codes is 4.
    private static final int maxDepth = Depth.maxDepth().getIndex() + 1;
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
      if (maxDepth < codeArr.length) {
        throw new RuntimeException("Invalid code for Genotyphi (too many levels " + codeArr.length + "): " + code);
      }

      final Depth depth = Depth.toDepth(codeArr.length).orElseThrow(() -> new RuntimeException("Not a recognised number of levels: " + codeArr.length));
      return new GenotyphiGroup(codeArr.length, Arrays.asList(codeArr));
    }

    public int getDepth() {

      return this.depth;
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
  }

}
