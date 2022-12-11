package fr.sma.aoc.adventofcode2022;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ResolveApplication implements CommandLineRunner {

	private final ApplicationContext applicationContext;
	private final DataFetcher dataFetcher;

	public ResolveApplication(ApplicationContext applicationContext, DataFetcher dataFetcher) {
		this.applicationContext = applicationContext;
		this.dataFetcher = dataFetcher;
	}

	public static void main(String[] args) {
		SpringApplication.run(ResolveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String day = args[0];
		String ex = args[1];

		int dayNum = Integer.parseInt(day);
		String beanName = String.format("day%02dEx%s", Integer.parseInt(day), ex);
		ExSolution exo = applicationContext.getBean(beanName, ExSolution.class);

		String data = dataFetcher.fetch(dayNum);
		String result = exo.run(data);
		System.out.println(exo.getClass().getSimpleName() + " : " + result);
	}
}
