package app.washtime.com.washtime.task;

import app.washtime.com.washtime.entity.Rule;

public interface RuleTask {

    Rule getRule(String locationName, String name, String floor);

    String saveRule(Rule rule);
}
