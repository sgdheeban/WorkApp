package com.workappinc.workappserver.businesslogic.pages;

/**
 * WorkAppUserProfilePage is an instance of IPage interface, which models the
 * user through a digital profile
 * 
 * @author dhgovindaraj
 *
 */
public class WorkAppUserProfilePage implements IPage
{

	/*
	 * Page and Section Load
	 */
	public synchronized static Object loadPage(Object userID)
	{
		return null;
	}

	public synchronized static Object loadSection(Object userID, Object sectionName)
	{
		return null;
	}

	/*
	 * Basic Profile Updates
	 */
	public synchronized static void editProfilePic(Object pic)
	{

	}

	public synchronized static void editBasicInfo(Object info)
	{

	}

	public synchronized static void editExperienceInfo()
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * Explicit Signal about Skill Set, Trusted Talents, Team Management
	 */
	public synchronized static void AddTrustedTalent(Object id)
	{

	}

	public synchronized static void RemoveTrustedTalent(Object id)
	{

	}

	public synchronized static void AddSkill(Object id)
	{

	}

	public synchronized static void RemoveSkill(Object id)
	{

	}

	public synchronized static void ListContacts(Object id)
	{

	}

	public synchronized static void CreateTeam(Object id)
	{
		throw new UnsupportedOperationException();
	}

	public synchronized static void DeleteTeam(Object id)
	{
		throw new UnsupportedOperationException();
	}

	public synchronized static void AddToTeam(Object id)
	{
		throw new UnsupportedOperationException();
	}

	public synchronized static void RemoveFromTeam(Object id)
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * Project Bidding and Ordering
	 */
	public synchronized static void BidOnProject(Object id)
	{

	}

	public synchronized static void BidOnTeamProject(Object id)
	{

	}

	public synchronized static void AcceptProject(Object id)
	{

	}

	public synchronized static void PlaceProjectOrder(Object id)
	{

	}

	/*
	 * Invitation Feature to increase word-of-mouth advertising
	 */
	public synchronized static void InviteLinkedInContacts(Object fbID)
	{

	}

	public synchronized static void InviteTwitterContacts(Object fbID)
	{

	}

	public synchronized static void InviteFacebookContacts(Object fbID)
	{

	}

	public synchronized static void InviteEMailContacts(Object emailID)
	{

	}

	public synchronized static void InvitePhoneContacts(Object emailID)
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * Sharing Feature to increase word-of-mouth advertising
	 */
	public synchronized static void ShareOnFB(Object fbID)
	{
		throw new UnsupportedOperationException();
	}

	public synchronized static void ShareOnTwitter(Object fbID)
	{
		throw new UnsupportedOperationException();
	}

	public synchronized static void ShareOnLinkedIn(Object fbID)
	{
		throw new UnsupportedOperationException();
	}

}
