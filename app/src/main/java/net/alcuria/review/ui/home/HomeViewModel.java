package net.alcuria.review.ui.home;

import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.Subject;
import net.alcuria.review.repository.SubjectRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Stores and manages UI-related data for the homepage in a lifecycle-conscious way.
 * Must never reference a View, Lifecycle, or any class that has a reference to the
 * activity's context.
 *
 * @author Andrew Keturi
 */
public class HomeViewModel extends ViewModel {

    private LiveData<ResponseData<Subject>> subjectData;

    public HomeViewModel(SubjectRepository subjectRepository) {
        subjectData = subjectRepository.getSubjects();
    }

    public LiveData<ResponseData<Subject>> getSubjectData() {
        return subjectData;
    }

}