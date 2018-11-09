package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticModel {

    private List<Dilemma> dilemmas;
    private List<Couple> couples;
    private List<Child> children;
    private List<Parent> parents;
    private List<Answer> answers;
    private List<Result> results;
    private List<Dilemma> filteredDilemmas;
    private List<Couple> filteredCouples;
    private List<Child> filteredChildren;
    private List<Parent> filteredParents;
    private List<Answer> filteredAnswers;
    private List<Result> filteredResults;

    public void setData(List<Dilemma> dilemmas, List<Parent> parents, List<Couple> couples, List<Child> children, List<Answer> answers, List<Result> results) {
        this.dilemmas = dilemmas;
        this.couples = couples;
        this.children = children;
        this.answers = answers;
        this.results = results;
        this.parents = parents;
    }

    public void resetFilters() {
        this.filteredAnswers = answers;
        this.filteredChildren = children;
        this.filteredCouples = couples;
        this.filteredDilemmas = dilemmas;
        this.filteredResults = results;
        this.filteredParents = parents;
    }

    public void initFilter() {
        resetFilters();
    }

    public void filterByAnswer(List<Answer> answers) {
        int[] answerIds = new int[answers.size()];
        for (int index = 0; index < answers.size(); index++) {
            Answer answer = answers.get(index);
            answerIds[index] = answer.getId();
        }
        filteredDilemmas = filterDilemmaByAnswer(answers);
        filteredResults = filterResultByAnswer(answers);
        filteredParents = filterParentByResult(filteredResults);
        filteredCouples = filterCouplesByParent(filteredParents);
        filteredChildren = filterChilderenByCouples(filteredCouples);
        filterByAnswerIds(answerIds);
    }

    public void filterByChild(List<Child> children) {
        int[] coupleIds = new int[children.size()];
        int[] childerenIds = new int[children.size()];
        for (int index = 0; index < children.size(); index++) {
            Child child = children.get(index);
            childerenIds[index] = child.getId();
            coupleIds[index] = child.getCouple_id();
        }
        filteredCouples = filterCouplesByChilderen(children);
        filteredParents = filterParentsByCouples(filteredCouples);
        filteredResults = filterResultByParent(filteredParents);
        filteredAnswers = filterAnswerByResult(filteredResults);
        filteredDilemmas = filterDilemmaByAnswer(filteredAnswers);
        filterByChildIds(childerenIds);
    }

    public void filterByCouple(List<Couple> couples) {
        int[] coupleIds = new int[couples.size()];
        for (int index = 0; index < couples.size(); index++) {
            Couple couple = couples.get(index);
            coupleIds[index] = couple.getId();
        }
        filteredChildren = filterChilderenByCouples(couples);
        filteredParents = filterParentsByCouples(couples);
        filteredResults = filterResultByParent(filteredParents);
        filteredAnswers = filterAnswerByResult(filteredResults);
        filteredDilemmas = filterDilemmaByAnswer(filteredAnswers);
        filterByCoupleIds(coupleIds);
    }

    private List<Child> filterChilderenByCouples(List<Couple> couples) {
        List<Child> childs = new ArrayList<>();
        couples.forEach(couple -> {
            List<Child> filter = filteredChildren.stream().
                    filter(child -> child.getCouple_id() == couple.getId())
                    .collect(Collectors.toList());
            childs.addAll(filter);
        });
        return childs.stream().distinct().collect(Collectors.toList());
    }

    private List<Couple> filterCouplesByChilderen(List<Child> children) {
        List<Couple> couples = new ArrayList<>();
        children.forEach(child -> {
            List<Couple> filter = filteredCouples.stream().
                    filter(couple -> child.getCouple_id() == couple.getId())
                    .collect(Collectors.toList());
            couples.addAll(filter);
        });
        return couples.stream().distinct().collect(Collectors.toList());
    }

    private List<Parent> filterParentsByCouples(List<Couple> couples) {
        List<Parent> parents = new ArrayList<>();
        couples.forEach(couple -> {
            List<Parent> filter = filteredParents.stream().
                    filter(parent -> parent.getId() == couple.getParent1_id() || parent.getId() == couple.getParent2_id())
                    .collect(Collectors.toList());
             parents.addAll(filter);
        });
        return parents.stream().distinct().collect(Collectors.toList());
    }

    private List<Couple> filterCouplesByParent(List<Parent> parents) {
        List<Couple> couples = new ArrayList<>();
        parents.forEach(parent -> {
            List<Couple> filter = filteredCouples.stream().
                    filter(couple -> parent.getId() == couple.getParent1_id() || parent.getId() == couple.getParent2_id())
                    .collect(Collectors.toList());
            couples.addAll(filter);
        });
        return couples.stream().distinct().collect(Collectors.toList());
    }

    private List<Result> filterResultByParent(List<Parent> parents) {
        List<Result> results = new ArrayList<>();
        parents.forEach(parent -> {
            List<Result> filter = filteredResults.stream().
                    filter(result -> result.getParent_id() == parent.getId())
                    .collect(Collectors.toList());
            results.addAll(filter);
        });
        return results.stream().distinct().collect(Collectors.toList());
    }

    private List<Parent> filterParentByResult(List<Result> results) {
        List<Parent> parents = new ArrayList<>();
        results.forEach(result -> {
            List<Parent> filter = filteredParents.stream().
                    filter(parent -> result.getParent_id() == parent.getId())
                    .collect(Collectors.toList());
            parents.addAll(filter);
        });
        return parents.stream().distinct().collect(Collectors.toList());
    }

    private List<Answer> filterAnswerByResult(List<Result> results) {
        List<Answer> answers = new ArrayList<>();
        results.forEach(result -> {
            List<Answer> filter = filteredAnswers.stream().
                    filter(answer -> answer.getId() == result.getAnswer_id())
                    .collect(Collectors.toList());
            answers.addAll(filter);
        });
        return answers.stream().distinct().collect(Collectors.toList());
    }

    private List<Result> filterResultByAnswer(List<Answer> answers) {
        List<Result> results = new ArrayList<>();
        answers.forEach(answer -> {
            List<Result> filter = filteredResults.stream().
                    filter(result -> answer.getId() == result.getAnswer_id())
                    .collect(Collectors.toList());
            results.addAll(filter);
        });
        return results.stream().distinct().collect(Collectors.toList());
    }

    private List<Dilemma> filterDilemmaByAnswer(List<Answer> answers) {
        List<Dilemma> dilemmas = new ArrayList<>();
        answers.forEach(answer -> {
            List<Dilemma> filter = filteredDilemmas.stream().
                    filter(dilemma -> dilemma.getId() == answer.getDilemma_id())
                    .collect(Collectors.toList());
            dilemmas.addAll(filter);
        });
        return dilemmas.stream().distinct().collect(Collectors.toList());
    }

    private List<Answer> filterAnswerByDilemma(List<Dilemma> dilemmaList) {
        List<Answer> answers = new ArrayList<>();
        dilemmaList.forEach(dilemma -> {
            List<Answer> filter = filteredAnswers.stream().
                    filter(answer -> dilemma.getId() == answer.getDilemma_id())
                    .collect(Collectors.toList());
            answers.addAll(filter);
        });
        return answers.stream().distinct().collect(Collectors.toList());
    }

    public void filterByDilemma(List<Dilemma> dilemmas) {
        int[] dilemmaIds = new int[dilemmas.size()];
        for (int index = 0; index < dilemmas.size(); index++) {
            Dilemma dilemma = dilemmas.get(index);
            dilemmaIds[index] = dilemma.getId();
        }
        filterByDilemmaIds(dilemmaIds);
        filteredAnswers = filterAnswerByDilemma(dilemmas);
        filteredResults = filterResultByAnswer(filteredAnswers);
        filteredParents = filterParentByResult(filteredResults);
        filteredCouples = filterCouplesByParent(filteredParents);
        filteredChildren = filterChilderenByCouples(filteredCouples);
    }

    public void filterByResult(List<Result> results) {
        int[] resultIds = new int[results.size()];
        for (int index = 0; index < results.size(); index++) {
            Result result = results.get(index);
            resultIds[index] = result.getId();
        }
        filterByResultIds(resultIds);
        filteredAnswers = filterAnswerByResult(results);
        filteredDilemmas = filterDilemmaByAnswer(filteredAnswers);
        filteredParents = filterParentByResult(results);
        filteredCouples = filterCouplesByParent(filteredParents);
        filteredChildren = filterChilderenByCouples(filteredCouples);
    }

    public void filterByHour(int hour) {
        filterByResult(filteredResults.stream().filter(result -> {
            if (result.getAnsweredTime() != null)
                return result.getAnsweredTime().getHours() == hour;
            return false;
        }).collect(Collectors.toList()));
    }

    public void filterByBronStatus (boolean status) {
        List<Child> filterBorn = filteredChildren.stream().filter(child -> child.getIsBorn() == status).collect(Collectors.toList());
        filterByChild(filterBorn);
    }


    @SuppressWarnings("unchecked")
    private void filterByDilemmaIds(int[] ids) {
        filteredDilemmas = filterById((List<DatabaseObject>)(List<?>)filteredDilemmas, ids);
    }

    @SuppressWarnings("unchecked")
    private void filterByAnswerIds(int[] ids){
        filteredAnswers = filterById((List<DatabaseObject>)(List<?>)filteredAnswers, ids);
    }

    @SuppressWarnings("unchecked")
    private void filterByChildIds(int[] ids){
        filteredChildren = filterById((List<DatabaseObject>)(List<?>)filteredChildren, ids);
    }

    @SuppressWarnings("unchecked")
    private void filterByCoupleIds(int[] ids){
        filteredCouples = filterById((List<DatabaseObject>)(List<?>)filteredCouples, ids);
    }

    @SuppressWarnings("unchecked")
    private void filterByParentIds(int[] ids){
        filteredParents = filterById((List<DatabaseObject>)(List<?>)filteredParents, ids);
    }

    @SuppressWarnings("unchecked")
    private void filterByResultIds(int[] ids){
        filteredResults = filterById((List<DatabaseObject>)(List<?>)filteredResults, ids);
    }

    private List filterById(List<DatabaseObject> list, int[] ids) {
        return list.stream().filter(object -> {
            for (int objectId : ids){
                if (object.getId() == objectId) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    public List<Dilemma> getFilteredDilemmas() {
        return filteredDilemmas;
    }

    public List<Couple> getFilteredCouples() {
        return filteredCouples;
    }

    public List<Child> getFilteredChildren() {
        return filteredChildren;
    }

    public List<Parent> getFilteredParents() {
        return filteredParents;
    }

    public List<Answer> getFilteredAnswers() {
        return filteredAnswers;
    }

    public List<Result> getFilteredResults() {
        return filteredResults;
    }

    @Override
    public String toString() {
        return "StatisticModel{" +
                ", dilemmas=" + dilemmas +
                ", couples=" + couples +
                ", childeren=" + children +
                ", parents=" + parents +
                ", answers=" + answers +
                ", results=" + results +
                '}';
    }
}
