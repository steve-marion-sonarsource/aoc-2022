package fr.sma.aoc.adventofcode2022.solution.day19;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day19Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "19", "1");
  }

  @Override
  public String run(String input) throws Exception {
    int qualityLevel = StreamEx.split(input.trim(), "\n")
      .map(Blueprint::new)
      .mapToInt(blueprint -> {
        int remainingTime = 24;
        Set<State> states = Set.of(new State(new Stockpile(), new Staff(), null));
        while (remainingTime > 0) {
          Set<State> nextStates = new HashSet<>();
          for (State s : states) {
            nextStates.addAll(exploreGreedyBuild(blueprint, s));
          }
          //System.out.println("blueprint " + blueprint.blueprintId + " - " + remainingTime + " : " + nextStates.size() + " states possible");
          states = nextStates.stream()
            .sorted(Comparator.reverseOrder())
            .limit(200_000)
            .collect(Collectors.toCollection(HashSet::new));
          remainingTime--;
        }
        State maxGeode = states.stream()
          .max(Comparator.comparingInt(s -> s.stock.geode))
          .orElseThrow();
        System.out.println("history of blueprint " + blueprint.blueprintId + ":\n" + maxGeode.printHistory(24));
        System.out.println("blueprint " + blueprint.blueprintId + " : " + maxGeode.stock.geode + " geode at most");
        maxGeode.checkRun(blueprint);
        return maxGeode.stock.geode * blueprint.blueprintId;
      }).sum();

    return String.valueOf(qualityLevel);
  }

  public Set<State> exploreGreedyBuild(Blueprint blueprint, State state) {
    Set<State> states = new HashSet<>();
    Stockpile geStock = state.stock.copy();
    Staff geStaff = state.staff.copy();

    while (blueprint.buildGeoRobot(geStock, geStaff)) ;
    while (blueprint.buildObsRobot(geStock, geStaff)) ;
    do {
      Stockpile orStock = geStock.copy();
      Staff orStaff = geStaff.copy();
      while (blueprint.buildOreRobot(orStock, orStaff)) ;
      states.add(new State(state.staff.work(orStock.copy()), orStaff, state));
    } while (blueprint.buildClayRobot(geStock, geStaff));
    return states;
  }

  public Set<State> exploreAllPossibleBuild(Blueprint blueprint, State state) {
    Set<State> states = new HashSet<>();
    Stockpile geStock = state.stock.copy();
    Staff geStaff = state.staff.copy();
    do {
      Stockpile obStock = geStock.copy();
      Staff obStaff = geStaff.copy();
      do {
        Stockpile clStock = obStock.copy();
        Staff clStaff = obStaff.copy();
        do {
          Stockpile orStock = clStock.copy();
          Staff orStaff = clStaff.copy();
          do {
            Stockpile nextStock = state.staff.work(orStock.copy());
            states.add(new State(nextStock, orStaff, state));
          } while (blueprint.buildOreRobot(orStock, orStaff));
        } while (blueprint.buildClayRobot(clStock, clStaff));
      } while (blueprint.buildObsRobot(obStock, obStaff));
    } while (blueprint.buildGeoRobot(geStock, geStaff));
    return states;
  }

  private class State implements Comparable<State> {
    public final Stockpile stock;
    public final Staff staff;
    public final State previous;

    public State(Stockpile stock, Staff staff, State previous) {
      this.stock = stock;
      this.staff = staff;
      this.previous = previous;
    }

    public void checkRun(Blueprint b) {
      if(previous != null) {
        b.checkTurn(previous, this);
        previous.checkRun(b);
      }
    }

    public String printHistory(int num) {
      if(previous != null) {
        return previous.printHistory(num - 1) + "\n"+ String.format("%2d", num) + ": " + this;
      }
      return toString();
    }

    @Override
    public String toString() {
      return String.format("%2d(%2d), %2d(%2d), %2d(%2d), %2d(%2d)", staff.rOre, stock.ore, staff.rClay, stock.clay, staff.rObs, stock.obs, staff.rGeode, stock.geode);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      State state = (State) o;

      if (!stock.equals(state.stock)) return false;
      return staff.equals(state.staff);
    }

    @Override
    public int hashCode() {
      int result = stock.hashCode();
      result = 31 * result + staff.hashCode();
      return result;
    }

    @Override
    public int compareTo(State o) {
      if (o.stock.geode != this.stock.geode) {
        return Integer.compare(this.stock.geode, o.stock.geode);
      }
      if (o.stock.obs != this.stock.obs) {
        return Integer.compare(this.stock.obs, o.stock.obs);
      }
      if (o.stock.clay != this.stock.clay) {
        return Integer.compare(this.stock.clay, o.stock.clay);
      }
      return Integer.compare(this.stock.ore, o.stock.ore);
    }
  }

  public static class Stockpile {
    int ore;
    int clay;
    int obs;
    int geode;

    public Stockpile() {
      this(0, 0, 0, 0);
    }

    public Stockpile copy() {
      return new Stockpile(ore, clay, obs, geode);
    }

    public Stockpile(int ore, int clay, int obs, int geode) {
      this.ore = ore;
      this.clay = clay;
      this.obs = obs;
      this.geode = geode;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Stockpile stockpile = (Stockpile) o;

      if (ore != stockpile.ore) return false;
      if (clay != stockpile.clay) return false;
      if (obs != stockpile.obs) return false;
      return geode == stockpile.geode;
    }

    @Override
    public int hashCode() {
      int result = ore;
      result = 31 * result + clay;
      result = 31 * result + obs;
      result = 31 * result + geode;
      return result;
    }
  }

  public static class Staff {
    int rOre;
    int rClay;
    int rObs;
    int rGeode;

    public Staff() {
      this(1, 0, 0, 0);
    }

    public Staff copy() {
      return new Staff(rOre, rClay, rObs, rGeode);
    }

    public Staff(int rOre, int rClay, int rObs, int rGeode) {
      this.rOre = rOre;
      this.rClay = rClay;
      this.rObs = rObs;
      this.rGeode = rGeode;
    }

    public Stockpile work(Stockpile previous) {
      previous.ore += rOre;
      previous.clay += rClay;
      previous.obs += rObs;
      previous.geode += rGeode;
      return previous;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Staff staff = (Staff) o;

      if (rOre != staff.rOre) return false;
      if (rClay != staff.rClay) return false;
      if (rObs != staff.rObs) return false;
      if (rGeode != staff.rGeode) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = rOre;
      result = 31 * result + rClay;
      result = 31 * result + rObs;
      result = 31 * result + rGeode;
      return result;
    }
  }

  public static class Run {
    private Stockpile stock;
    private Staff robots;

    public Run copy() {
      return new Run(stock.copy(), robots.copy());
    }

    private Run(Stockpile stock, Staff robots) {
      this.stock = stock;
      this.robots = robots;
    }

    public Run() {
      stock = new Stockpile();
      robots = new Staff();
    }

    public boolean buildRobot(BiPredicate<Stockpile, Staff> order) {
      return order.test(stock, robots);
    }

    public void produce() {
      robots.work(stock);
    }
  }

  public static class Blueprint {
    private static final Pattern blueprintPattern = Pattern.compile("Blueprint (?<blueprintId>\\d+): Each ore robot costs (?<orerOreCost>\\d+) ore. Each clay robot costs (?<clarOreCost>\\d+) ore. Each obsidian robot costs (?<obsrOreCost>\\d+) ore and (?<obsrClaCost>\\d+) clay. Each geode robot costs (?<georOreCost>\\d+) ore and (?<georObsCost>\\d+) obsidian.");

    private final int blueprintId;
    private final int orerOreCost;
    private final int clarOreCost;
    private final int obsrOreCost;
    private final int obsrClaCost;
    private final int georOreCost;
    private final int georObsCost;

    public Blueprint(String blueprintText) {
      Matcher matcher = blueprintPattern.matcher(blueprintText);
      if (!matcher.matches()) {
        throw new IllegalArgumentException(blueprintText + " does not match");
      }
      blueprintId = Integer.parseInt(matcher.group("blueprintId"));
      orerOreCost = Integer.parseInt(matcher.group("orerOreCost"));
      clarOreCost = Integer.parseInt(matcher.group("clarOreCost"));
      obsrOreCost = Integer.parseInt(matcher.group("obsrOreCost"));
      obsrClaCost = Integer.parseInt(matcher.group("obsrClaCost"));
      georOreCost = Integer.parseInt(matcher.group("georOreCost"));
      georObsCost = Integer.parseInt(matcher.group("georObsCost"));
    }

    private boolean buildOreRobot(Stockpile from, Staff to) {
      if (from.ore >= orerOreCost) {
        from.ore -= orerOreCost;
        to.rOre++;
        return true;
      }
      return false;
    }

    private boolean canBuildOreRobot(Stockpile from) {
      return from.ore >= orerOreCost;
    }

    private boolean buildClayRobot(Stockpile from, Staff to) {
      if (from.ore >= clarOreCost) {
        from.ore -= clarOreCost;
        to.rClay++;
        return true;
      }
      return false;
    }

    private boolean canBuildClayRobot(Stockpile from) {
      return from.ore >= clarOreCost;
    }

    private boolean buildObsRobot(Stockpile from, Staff to) {
      if (from.ore >= obsrOreCost && from.clay >= obsrClaCost) {
        from.ore -= obsrOreCost;
        from.clay -= obsrClaCost;
        to.rObs++;
        return true;
      }
      return false;
    }

    private boolean canBuildObsRobot(Stockpile from) {
      return from.ore >= obsrOreCost && from.clay >= obsrClaCost;
    }

    private boolean buildGeoRobot(Stockpile from, Staff to) {
      if (from.ore >= georOreCost && from.obs >= georObsCost) {
        from.ore -= georOreCost;
        from.obs -= georObsCost;
        to.rGeode++;
        return true;
      }
      return false;
    }

    private boolean canBuildGeoRobot(Stockpile from) {
      return from.ore >= georOreCost && from.obs >= georObsCost;
    }

    public void checkTurn(State from, State to) {
      boolean geoCheck = to.stock.geode == from.stock.geode + from.staff.rGeode;
      if(!geoCheck) {
        System.out.printf("geode calculation fail between these states: %n%s%n%s", from, to);
      }
      boolean obsCheck = to.stock.obs == from.stock.obs + from.staff.rObs - (to.staff.rGeode - from.staff.rGeode)*this.georObsCost;
      if(!obsCheck) {
        System.out.printf("obsidian calculation fail between these states: %n%s%n%s", from, to);
      }
      boolean claCheck = to.stock.clay == from.stock.clay + from.staff.rClay - (to.staff.rObs - from.staff.rObs)*this.obsrClaCost;
      if(!claCheck) {
        System.out.printf("clay calculation fail between these states: %n%s%n%s", from, to);
      }
      boolean oreCheck = to.stock.ore == from.stock.ore + from.staff.rOre
        - (to.staff.rGeode - from.staff.rGeode)*this.georOreCost
        - (to.staff.rObs - from.staff.rObs)*this.obsrOreCost
        - (to.staff.rClay - from.staff.rClay)*this.clarOreCost
        - (to.staff.rOre - from.staff.rOre)*this.orerOreCost;
      if(!oreCheck) {
        System.out.printf("ore calculation fail between these states: %n%s%n%s", from, to);
      }
    }
  }
}
