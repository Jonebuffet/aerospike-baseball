
package com.aerospike.app.seed;


import com.aerospike.app.seed.SeedUtils;

import java.util.Random;


public abstract class Seeder<T> {

  protected Random random;
  protected static SeedUtils seedUtils = new SeedUtils();
    
  protected Seeder() {
    this.random = new Random();
  }

  protected Seeder(Random random) {
    this.random = random;
  }

  protected Seeder(long seed) {
    this.random = new Random(seed);
  }
    
  public abstract T get();

} // Seeder
