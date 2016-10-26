package com.iliani14.pg6100.ejb;

import com.iliani14.pg6100.entity.SubCategory;
import com.iliani14.pg6100.entity.SubSubCategory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by anitailieva on 26/10/2016.
 */

@Stateless
public class SubSubCategoryEJB {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private SubCategoryEJB subCategoryEJB;


    public SubSubCategory createSubSubCategory(SubCategory subCategory, String name){
        SubSubCategory subsub = new SubSubCategory();
        subsub.setName(name);

        em.persist(subsub);

        SubCategory sub = subCategoryEJB.findSubCategoryById(subCategory.getId());
        sub.getSubSubCategories().add(subsub);

        return subsub;
    }

    public SubSubCategory findSubSubCategoryById(long id){
        return em.find(SubSubCategory.class, id);

    }

    public List<SubSubCategory> getAllSubSubCategories(){
        Query query = em.createNamedQuery(SubSubCategory.GET_ALL_SUBSUBCATEGORIES);
        List<SubSubCategory> subSubCategories = query.getResultList();

        return subSubCategories;
    }

    public SubSubCategory getSubSubCategoryBySubCategoryName(String subCategoryName){
        Query query = em.createNamedQuery(SubSubCategory.GET_SUBSUBCATEGORY_BY_SUBCATEGORY);
        query.setParameter("subCategoryName", subCategoryName);

        return (SubSubCategory) query.getResultList();
    }

    public void deleteSubSubCategory(long id){
        Query query = em.createQuery("DELETE FROM SubSubCategory s WHERE s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
