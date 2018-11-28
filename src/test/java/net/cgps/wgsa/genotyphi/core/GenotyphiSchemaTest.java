package net.cgps.wgsa.genotyphi.core;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenotyphiSchemaTest {

  @Test
  public void isParentOf() {

    final GenotyphiSchema.GenotyphiGroup group1 = GenotyphiSchema.GenotyphiGroup.build("1");
    final GenotyphiSchema.GenotyphiGroup group2 = GenotyphiSchema.GenotyphiGroup.build("2");
    final GenotyphiSchema.GenotyphiGroup group1_2 = GenotyphiSchema.GenotyphiGroup.build("1.2");
    final GenotyphiSchema.GenotyphiGroup group1_2_3 = GenotyphiSchema.GenotyphiGroup.build("1.2.P1");

    Assert.assertFalse(group1.isParentOf(group2));
    Assert.assertFalse(group2.isParentOf(group1));

    Assert.assertTrue(group1.isParentOf(group1_2));
    Assert.assertTrue(group1.isParentOf(group1_2_3));
    Assert.assertTrue(group1_2.isParentOf(group1_2_3));

    Assert.assertFalse(group2.isParentOf(group1_2));
    Assert.assertFalse(group1_2.isParentOf(group1));
  }
}