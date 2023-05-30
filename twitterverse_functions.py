"""CSC108: Fall 2020 -- Assignment 3: Twitterverse 

This code is provided solely for the personal and private use of students 
taking the CSC108 course at the University of Toronto. Copying for purposes 
other than this use is expressly prohibited. All forms of distribution of 
this code, whether as given or with any changes, are expressly prohibited.

All of the files in this directory and all subdirectories are:
Copyright (c) 2020 Mario Badr, Jennifer Campbell, Tom Fairgrieve, Diane Horton, 
Michael Liut, Jacqueline Smith, and Anya Tafliovich.
"""

from typing import Callable, Dict, List, TextIO

"""Type descriptions of TwitterverseDict, QueryDict, SearchDict, FilterDict, 
and SortingDict dictionaries. 

We use these types to simplify our type contracts, and to capture additional
information about each type, as indicated below.

You should use these types in your type contracts, inside single quotes.
e.g. def process_data(file: TextIO) -> 'TwitterverseDict':

TwitterverseDict
Twitterverse dictionary: Dict[str, Dict[str, object]]
    - each key is a username (a str)
    - each value is a Dict[str, object] with items as follows:
        - key 'name', value represents a user's name (a str)
        - key 'location', value represents a user's location (a str)
        - key 'web', value represents a user's website (a str)
        - key 'bio', value represents a user's bio (a str)
        - key 'following', value usernames of users this user is following 
          (a List[str])

QueryDict
Query dictionary: Dict[str, Dict[str, object]]
    - key 'search', value represents a search specification dictionary
    - key 'filter', value represents a filter specification dictionary
    - key 'sorting', value represents a sorting specification dictionary

SearchDict
Search specification dictionary: Dict[str, object]
    - key 'username', value represents username to begin search at (a str)
    - key 'operations', value represents the operations to perform 
      (a List[str])

FilterDict
Filter specification dictionary: Dict[str, str]
    - key 'following' might exist, value represents a username (a str)
    - key 'follower' might exist, value represents a username (a str)
    - key 'name-includes' might exist, value represents str to match 
      (a case-insensitive match)
    - key 'location-includes' might exist, value represents str to match 
      (a case-insensitive match)
    - key 'bio-includes' might exist, value represents str to match 
      (a case-insensitive match)

SortingDict
Sorting specification dictionary: Dict[str, str]
    - key 'sort-by', value represents how to sort results (a str)

"""
  
     
def process_data(file: TextIO) -> 'TwitterverseDict':
    """Return the Twitterverse dictionary containing the Twitter data in file.
    
    No examples for file reading functions.
    """
    twtverse = {}
    check = ''
    
    while check != 'END':
        
        username = file.readline().strip()
        name = file.readline().strip()
        location = file.readline().strip()
        site = file.readline().strip()

        twtverse[username] = {}

        twtverse[username]['name'] = name
        twtverse[username]['location'] = location
        twtverse[username]['web'] = site
        
        bio = ''
        line = file.readline()

        while line != 'ENDBIO\n':
            bio += line
            line = file.readline()
        
        bio = bio.replace('\n', '')
        twtverse[username]['bio'] = bio
        
        following = []
        follow = file.readline()

        while follow != 'END\n' and follow != 'END':
            following.append(follow.strip())
            follow = file.readline()  
            if follow == 'END':
                check = 'END'
                follow = 'END\n'
                
        if follow == 'END':
            check = 'END'
        twtverse[username]['following'] = following
                
    return twtverse

def process_query(file: TextIO) -> 'QueryDict':
    """Return the Query dictionary containing the query data in file.
    
    No examples for file reading functions.
    """
    query_dict = {'search':{}, 'filter':{}, 'sorting':{}}
    
    operations = []
    #get rid of unneeded 'SEARCH\n' line
    file.readline()
    
    query_dict['search']['username'] = file.readline().strip()
    line = file.readline()
    
    while line != 'FILTER\n':
        operations.append(line.strip())
        line = file.readline()
        
    query_dict['search']['operations'] = operations
    
    info = []
    # get rid of unneeded 'FILTER\n' line
    file.readline()
    
    line = file.readline()
    while line != 'SORT\n':
        info = line.split(' ')
        query_dict['filter'][info[0]] = info[1].replace('\n', '')
        line = file.readline()
        info = []
    
    # get rid of unneeded 'SORT\n' line
    file.readline()
        
    line = file.readline()
    while line != '':
        query_dict['sorting']['sort-by'] = line.replace('\n', '')
        line = file.readline()    

    return query_dict


def all_followers(twtverse: 'TwitterverseDict', username: str) -> List[str]:
    """Returns a list containing all the usernames that are following the 
    user specified by the second parameter. 
    
    >>> example_data = {'tomCruise': {'name': 'Tom Cruise', 
                              'location': 'Los Angeles, CA', 
                              'web': 'http://www.tomcruise.com', 
                              'bio': 'Official TomCruise.com crew tweets. '+ \
                              'We love you guys!\nVisit us at Facebook!', 
                              'following': ['katieH']}, 
                'katieH': {'name': 'Katie Holmes', 'location': '', 
                           'web': 'www.tomkat.com', 'bio': '', 'following': []}}
    >>> all_followers(example_data, tomCruise)
    >>> []
    
    >>> twitter_data = {'tomCruise': {'name': 'Tom Cruise', 
                              'location': 'Los Angeles, CA', 
                              'web': 'http://www.tomcruise.com', 
                              'bio': 'Official TomCruise.com crew tweets. '+ \
                              'We love you guys!\nVisit us at Facebook!', 
                              'following': ['katieH']}, 
                'katieH': {'name': 'Katie Holmes', 'location': '', 
                           'web': 'www.tomkat.com', 'bio': '', 'following': []}}
    >>> all_followers(twitter_data, katieH)
    >>> ['tomCruise']
    
    >>> shen_data = \
            {'claireshen': {'name': 'Claire Shen', 
                           'location': 'Thornhill, ON', 
                           'web': '', 
                           'bio': '19', 
                           'following': ['katieH', 'kaitlynshen']},
             'kaitlynshen': {'name': 'Kaitlyn Shen', 
                           'location': 'Toronto, ON', 
                           'web': '', 
                           'bio': 'NBS <3', 
                           'following': ['katieH', 'claireshen']},
             'chloeyshen': {'name': 'Chloey Shen', 
                           'location': 'Thornhill, ON', 
                           'web': '', 
                           'bio': 'Kaitlyn and Kevin are dumb.', 
                           'following': ['katieH', 'claireshen']},
             'kevinshen': {'name': 'Kevin Shen', 
                           'location': 'Thornhill, ON', 
                           'web': 'roblox.com', 
                           'bio': 'Busy gaming', 
                           'following': ['katieH', 'claireshen']}}
    >>> all_followers(shen_data, claireshen)
    >>> ['kaitlynshen', 'chloeyshen', 'kevinshen']
    """
    
    followers = []
    for key in twtverse.keys():
        if username in twtverse[key]['following']:
            followers.append(key)
    
    return followers
    
    
def get_search_results(twtverse: 'TwitterverseDict', 
                       search: 'SearchDict') -> List[str]:
    """Returns list of usernames that match the search criteria
    >>> twitterverse_dict = {'a': {'bio': '', 'name': 'Allison Martian',
                                   'location': '', 'web': '', 
                                   'following': ['c','b']},
                             'b': {'bio': '', 'name': 'Barry B',
                                   'location': '', 'web': '', 
                                   'following': ['c']},
                             'c': {'bio': '', 'name': 'Clara Panasonic',
                                   'location': '', 'web': '', 
                                   'following': []}}
    >>> query_dict = {'username': 'c', 
                              'operations' : 
                              ['followers','following','following']}
    >>> get_search_results(twitterverse_dict, query_dict)
    ['c']
    
    
    >>> twitterverse_dict = {'a': {'bio': '', 'name': 'Allison Martini',
                                   'location': '', 'web': '', 
                                   'following': ['c','b']},
                             'b': {'bio': '', 'name': 'Barry B',
                                   'location': '', 'web': '', 
                                   'following': ['c']},
                             'c': {'bio': '', 'name': 'Clara Panasonic',
                                   'location': '', 'web': '', 
                                   'following': []}}
    >>> query_dict = {'username': 'a', 'operations' : []}
    ['a']
    """
    process = []
    results = []
    results.append(search['username'])
    operations = search['operations']
    
    i = 0 
    while i < len(operations):
        if operations[i] == 'following':
            process = []
            for user in results:
                process.extend(twtverse[user]['following'])
        elif operations[i] == 'followers':
            process = []
            for user in results:
                process.extend(all_followers(twtverse, user))
                      
        results = []
        results.extend(list(set(process)))
        i = i + 1
    
    return results

    
def get_filter_results(twtverse: 'TwitterverseDict', usernames: List[str],
                       filt_dict: 'FilterDict') -> List[str]:
    """Returns a list of usernames filtered by the specific filters
    """
    results = usernames
    
    for key in filt_dict:
        if key == 'name_includes':
            results = name_includes_filter(twtverse, results, filt_dict[key])
        elif key == 'location_includes':
            results = location_includes_filter(twtverse, 
                                               results, filt_dict[key])     
        elif key == 'bio_includes':
            results = bio_includes_filter(twtverse, results, filt_dict[key])   
        elif key == 'follower':
            results = follower_filter(twtverse, results, filt_dict[key])     
        elif key == 'following':
            results = following_filter(twtverse, results, filt_dict[key])          
    
    return results

def name_includes_filter(twtverse: 'TwitterverseDict', 
                         users: List[str], included: str) -> List[str]:
    """Returns a list of usernames whose name includes specified string
    """
    for item in users:
        if included.upper() not in twtverse[item]['name'].upper():
            users.remove(item)
    
    return users  

def location_includes_filter(twtverse: 'TwitterverseDict', users: List[str],
                             included: str) -> List[str]:
    """Returns a list of usernames whose location includes specified string
    """    
    for item in users:
        if included.upper() not in twtverse[item]['location'].upper():
            users.remove(item)
    
    return users

def bio_includes_filter(twtverse: 'TwitterverseDict', users: List[str],
                        included: str) -> List[str]:
    """Returns a list of usernames whose bio includes specified string
    """    
    for item in users:
        if included.upper() not in twtverse[item]['bio'].upper():
            users.remove(item)
    
    return users    

def follower_filter(twtverse: 'TwitterverseDict', users: List[str],
                    user: str) -> List[str]:
    """Returns a list of usernames whose follow the user
    """
    for item in users:
        if user not in twtverse[item]['following']:
            users.remove(item)
    
    return users
       

def following_filter(twtverse: 'TwitterverseDict', users: List[str],
                     user: str) -> List[str]:
    """Returns a list of usernames who user is following
    """    
    for item in users:
        if item not in twtverse[user]['following']:
            users.remove(item)
    
    return users


def get_sorted_results(twtverse: 'TwitterVerseDict', usernames: List[str],
                       sort_dict: 'SortingDict') -> List[str]:
    """Returns a list sorted by the given sorting specification
    """
    results = usernames
    if sort_dict['sort-by'] == 'username':
        tweet_sort(twtverse, usernames, compare_usernames)
    elif sort_dict['sort-by'] == 'name':
        tweet_sort(twtverse, usernames, compare_names)
    elif sort_dict['sort-by'] == 'popularity':
        tweet_sort(twtverse, usernames, compare_popularity)

    return results

def tweet_sort(twitter_data: 'TwitterverseDict', results: List[str], 
               comparison_func: Callable[['TwitterverseDict', str, str], int]
               ) -> None:
    """Modifies results to be sorted using the comparison function 
    comparison_func and the data in twitter_data.
    
    The type Callable[['TwitterverseDict', str, str], int] means a function
    that takes three arguments - a TwitterverseDict, and two strings to compare,
    and returns an int. 
    
    >>> twitter_data = {\
    'a':{'name':'Zoe', 'location':'', 'web':'', 'bio':'', 'following':['b']}, \
    'b':{'name':'Laverne', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'Ada', 'location':'', 'web':'', 'bio':'', 'following':['b', 'a']
    }}
    >>> results = ['c', 'a', 'b']
    >>> tweet_sort(twitter_data, results, compare_usernames)
    >>> results
    ['a', 'b', 'c']
    >>> tweet_sort(twitter_data, results, compare_names)
    >>> results
    ['c', 'b', 'a']
    >>> tweet_sort(twitter_data, results, compare_popularity)
    >>> results
    ['b', 'a', 'c']
    """
    
    # An implementation of Insertion Sort that uses a comparison function
    for i in range(1, len(results)):
        current = results[i]
        position = i
        while position > 0 and \
                comparison_func(twitter_data, results[position - 1], 
                                current) > 0:
            results[position] = results[position - 1]
            position = position - 1 
        results[position] = current  
       
def compare_popularity(twtverse: 'TwitterverseDict', a: str, b: str) -> int:
    """Returns -1 if user a has more followers than user b, 1 user a has less
    followers than user b, and sorts by usernames if equal
    
    >>> twitter_data = {\
    'a':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':['b']}, \
    'b':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> more_popular(twitter_data, 'a', 'b')
    1
    >>> more_popular(twitter_data, 'a', 'c')
    -1
    """
    a_pop = len(all_followers(twtverse, a))
    b_pop = len(all_followers(twtverse, b))
    
    if a_pop > b_pop:
        return -1
    if a_pop < b_pop:
        return 1
    
    return compare_usernames(twtverse, a, b)

def compare_names(twtverse: 'TwitterverseDict', a: str, b: str) -> int:
    """ Returns -1 if user a's name comes before user b's name alphabetically, 
    1 if user a's name comes after user b's name, sorts by usernames if equal
    
    >>> twitter_data = {\
    'a':{'name':'Zoe', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'b':{'name':'Laverne', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'Ada', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> name_first(twitter_data, 'c', 'b')
    1
    >>> name_first(twitter_data, 'b', 'a')
    -1
    """    
    a_name = twtverse[a]['name']
    b_name = twtverse[b]['name']
    
    if a_name > b_name:
        return 1
    if a_name < b_name:
        return -1
    
    return compare_usernames(twtverse, a, b)

def compare_usernames(twtverse: 'TwitterverseDict', a: str, b: str) -> int:
    """Returns -1 if user a has a username that comes before user b's username 
    alphabetically, 1 if user a's username comes after user b's username, 
    and 0 if they are the same
    
    >>> twitter_data = {\
    'a':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':['b']}, \
    'b':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}, \
    'c':{'name':'', 'location':'', 'web':'', 'bio':'', 'following':[]}}
    >>> compare_usernames(twitter_data, 'c', 'b')
    1
    >>> compare_usernames(twitter_data, 'a', 'b')
    -1
    """
    exists = False
    if a in twtverse.keys() and b in twtverse.keys():
        exists = True
        
    if exists:
        if a > b:
            return 1
        if a < b:
            return -1   
    
    return 0
    
    
if __name__ == '__main__':
    import doctest
    
    # Uncomment the call to doctest.testmod() to automatically run your
    # docstring examples when you run the twitterverse_functions.py file.
    # Note that your docstring examples must be perfectly formatted 
    # to be able to do this.
    
    # doctest.testmod()
    