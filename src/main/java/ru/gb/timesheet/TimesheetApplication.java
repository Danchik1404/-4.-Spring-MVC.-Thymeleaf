package ru.gb.timesheet;

/**
 * 1. Повторить все, что на семниаре
 * 2. В объект timesheet в поле createdAt должно подставляться текущее время на стороне сервера!
 * Т.е. не клиент присылает, а севрер устанавливает.
 * 3. Создать отдельный контроллер для проектов (поле Timesheet.project)
 * 3.1 Создать класс Project с полями id, name
 * 3.2 Создать CRUD-контроллер для класса Project, сервис и репозиторий
 * 3.3 В ресурсе Timesheet поле project изменить на projectId
 * 3.4 При создании Timesheet проверять, что project с идентификатором projectId существует
 *
 * * 4. Создать ресурс /projects/{id}/timesheets - загрузить таймашиты для конкретного проекта
 * ** 5. Создать ресурс /timesheets?createdAtAfter=2024-07-04
 *       - ручка для получения всех таймшитов, которые созданы ПОСЛЕ указанного параметра.
 *       Аналогично createdAtBefore
 */

/**
 * ВАЖНО!
 * 		Перед выполнением ДЗ предполагается, что ДЗ №3 выполнено (т.е. в проекте есть сущность Project).
 * 		Если это не реализовано - нужно сначала завершить ДЗ №3.
 *
 * 		1. Сделать страницу project-page.html по аналогии с timesheet-page.html
 * 		2. В timesheets-page в колонку "Проекты" добавить ссылку на проект
 * 		Для этого необходимо:
 * 		2.1 в TimesheetPageDto добавить поле projectId (для создания ссылки)
 * 		2.2 в timesheets-page в колонку "проекты" сделать гиперссылку (по аналогии с колонкой "перейти")
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimeSheetRepository;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TimesheetApplication.class, args);

		ProjectRepository projectRepo = context.getBean(ProjectRepository.class);

		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setProjectId((long) i);
			project.setName("Project #" + i);
			Project pr = projectRepo.create(project);
			System.out.println(pr);
		}

		TimeSheetRepository timesheetRepo = context.getBean(TimeSheetRepository.class);

		LocalDate createdAt = LocalDate.now();
		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setId((long) i);
			timesheet.setProjectId(String.valueOf(ThreadLocalRandom.current().nextLong(1, 6)));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

			timesheetRepo.create(timesheet);
			System.out.println(timesheet);
		}
	}
}