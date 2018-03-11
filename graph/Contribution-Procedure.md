## Watch the video 
https://www.youtube.com/playlist?list=PLhO3X757HABdao5hv4HGQSWPX0tnN4lCO
Use android studio built in git hub to push and pull

## Contribution Procedure
1. Clone the repository to your own machine.  
Clone with SSH:
```
$ git clone git@github.com:CMPUT301W18T25/ProjectMaster.git
```
Or, clone with HTTPS:
```
$ git clone https://github.com/CMPUT301W18T25/ProjectMaster.git
```

2. Create your own local branch.
```
$ git checkout -b username
```
If it is already created, simply do
```
$ git checkout username
```

3. Write code, add, and commit changes.

4. Push your changes to your own remote branch
```
$ git push origin username
```

5. Submit a PR to merge changes to master.

6. After PR is approved, master is ahead of your branch by 1 commit (the PR commit). Pull current master branch to get the PR commit.
```
$ git pull origin master
```

7. Push the PR commit to your own remote branch. At this point your branch is up-to-date.
```
$ git push origin username
```

### Note
1. All changes to the code in the future should be submitted as a PR (Pull Request), and each PR should be reviewed and tested by teammates before being merge to the master branch.
2. You need to regularly pull remote master to your local master.
```
$ git checkout master
$ git pull origin master
```
